#! /bin/bash

CONTAINER_ID=$(docker ps -q --filter "publish=5431")
PWD_FOR_LEARNING_PURPOSE=12345678

# Create directories in the Docker container
docker exec -it "$CONTAINER_ID" mkdir -p /scripts /sport_db

# Copy SQL scripts and CSV files to the Docker container
docker cp ./scripts/customers_table.sql "$CONTAINER_ID":/scripts/customers_table.sql
docker cp ./scripts/orders_table.sql "$CONTAINER_ID":/scripts/orders_table.sql
docker cp ./sport_db/Customer.csv "$CONTAINER_ID":/sport_db/Customer.csv
docker cp ./sport_db/Order.csv "$CONTAINER_ID":/sport_db/Order.csv

# Execute the database commands within the Docker container
docker exec -it "$CONTAINER_ID" bash -c "
  export PGUSER=sports_db_admin
  export PGPASSWORD=$PWD_FOR_LEARNING_PURPOSE
  dropdb sports_db_populated --force
  createdb sports_db_populated --owner=sports_db_admin
  psql -d sports_db_populated -f '/scripts/customers_table.sql'
  psql -d sports_db_populated -c \"\COPY Customers(ID,First_Name,Last_Name,Email,Phone,Address,City,State,Zipcode) FROM '/sport_db/Customer.csv' DELIMITER ',' CSV HEADER;\"
  psql -d sports_db_populated -f '/scripts/orders_table.sql'
  psql -d sports_db_populated -c \"\COPY Orders(ID,Date,Total_Due,Status,Customer_ID) FROM '/sport_db/Order.csv' DELIMITER ',' CSV HEADER;\"
"

docker exec -it "$CONTAINER_ID" rm -rf /scripts /sport_db
