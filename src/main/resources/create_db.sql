# cmd> psql -U postgres
#postgres=#>
CREATE DATABASE db_car WITH ENCODING 'UTF8';
CREATE USER user_car WITH PASSWORD '1234';
GRANT ALL PRIVILEGES ON DATABASE db_car TO user_car; 

## Connect to 'db_car' as postgres
# cmd> psql -U postgres db_car
## You are now connected to database "db_car" as user "postgres".
# db_car=#> 
GRANT ALL ON SCHEMA public TO user_car;
