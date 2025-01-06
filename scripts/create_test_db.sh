#! /bin/bash

CONTAINER_ID=$(docker ps -q --filter "publish=5431")
PWD_FOR_LEARNING_PURPOSE=12345678

docker exec -it "$CONTAINER_ID" bash -c "
  export PGUSER=sports_db_admin
  export PGPASSWORD=$PWD_FOR_LEARNING_PURPOSE
  dropdb sports_db_test --force
  createdb sports_db_test --owner=sports_db_admin
"
