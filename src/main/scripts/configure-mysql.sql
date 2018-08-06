#Create databases
CREATE DATABASE dantist_dev;
CREATE DATABASE dantist_prod;

#Create database service accounts
CREATE USER  'dantist_dev_user'@'localhost' IDENTIFIED BY 'ontario';
CREATE USER  'dantist_prod_user'@'localhost' IDENTIFIED BY 'ontario';
CREATE USER  'dantist_dev_user'@'%' IDENTIFIED BY 'ontario';
CREATE USER  'dantist_prod_user'@'%' IDENTIFIED BY 'ontario';

#Database grants
GRANT SELECT ON dantist_dev.* to 'dantist_dev_user'@'localhost';
GRANT INSERT ON dantist_dev.* to 'dantist_dev_user'@'localhost';
GRANT UPDATE ON dantist_dev.* to 'dantist_dev_user'@'localhost';
GRANT DELETE ON dantist_dev.* to 'dantist_dev_user'@'localhost';
GRANT SELECT ON dantist_prod.* to 'dantist_prod_user'@'localhost';
GRANT INSERT ON dantist_prod.* to 'dantist_prod_user'@'localhost';
GRANT UPDATE ON dantist_prod.* to 'dantist_prod_user'@'localhost';
GRANT DELETE ON dantist_prod.* to 'dantist_prod_user'@'localhost';
GRANT SELECT ON dantist_dev.* to 'dantist_dev_user'@'%';
GRANT INSERT ON dantist_dev.* to 'dantist_dev_user'@'%';
GRANT UPDATE ON dantist_dev.* to 'dantist_dev_user'@'%';
GRANT DELETE ON dantist_dev.* to 'dantist_dev_user'@'%';
GRANT SELECT ON dantist_prod.* to 'dantist_prod_user'@'%';
GRANT INSERT ON dantist_prod.* to 'dantist_prod_user'@'%';
GRANT UPDATE ON dantist_prod.* to 'dantist_prod_user'@'%';
GRANT DELETE ON dantist_prod.* to 'dantist_prod_user'@'%';