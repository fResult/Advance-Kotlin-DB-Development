#! /bin/bash

CONTAINER_ID=$(docker ps -q --filter "publish=5432")
docker exec -it "$CONTAINER_ID" dropdb sports_db_populated --force

docker exec -it "$CONTAINER_ID"createdb sports_db_populated --owner=sports_db_admin

docker exec -it "$CONTAINER_ID" sql -f "./scripts/customers_table.sql" sports_db_populated
docker exec -it "$CONTAINER_ID" sql -c "COPY Customers(ID,First_Name,Last_Name,Email,Phone,Address,City,State,Zipcode) \
FROM '$(pwd)/sport_db/Customer.csv' \
DELIMITER ',' \
CSV HEADER;" sports_db_populated

docker exec -it "$CONTAINER_ID" psql -f "./scripts/orders_table.sql" sports_db_populated
docker exec -it "$CONTAINER_ID" psql -c "COPY Orders(ID,Date,Total_Due,Status,Customer_ID) \
FROM '$(pwd)/sport_db/Order.csv' \
DELIMITER ',' \
CSV HEADER;" sports_db_populated
