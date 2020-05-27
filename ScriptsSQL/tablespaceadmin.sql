CREATE TABLESPACE "Restaurante" LOGGING
DATAFILE 'C:\oracleresadmine\app\oracle\oradata\XE\restaurante.dbf' SIZE 24M 
EXTENT MANAGEMENT LOCAL SEGMENT SPACE MANAGEMENT AUTO;

DROP USER resadmin CASCADE;

CREATE USER resadmin IDENTIFIED BY resadmin123
DEFAULT TABLESPACE "Restaurante"  
TEMPORARY TABLESPACE temp
ACCOUNT UNLOCK;

GRANT "CONNECT" TO resadmin;
GRANT "RESOURCE" TO resadmin;

GRANT ALTER ANY INDEX TO resadmin;
GRANT ALTER ANY SEQUENCE TO resadmin;
GRANT ALTER ANY TABLE TO resadmin;
GRANT ALTER ANY TRIGGER TO resadmin;
GRANT CREATE ANY INDEX TO resadmin;
GRANT CREATE ANY SEQUENCE TO resadmin;
GRANT CREATE ANY SYNONYM TO resadmin;
GRANT CREATE ANY TABLE TO resadmin;
GRANT CREATE ANY TRIGGER TO resadmin;
GRANT CREATE ANY VIEW TO resadmin;
GRANT CREATE PROCEDURE TO resadmin;
GRANT CREATE PUBLIC SYNONYM TO resadmin;
GRANT CREATE TRIGGER TO resadmin;
GRANT CREATE USER TO resadmin;
GRANT CREATE VIEW TO resadmin;
GRANT DELETE ANY TABLE TO resadmin;
GRANT DROP ANY INDEX TO resadmin;
GRANT DROP ANY SEQUENCE TO resadmin;
GRANT DROP ANY TABLE TO resadmin;