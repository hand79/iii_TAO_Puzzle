CREATE USER YA803G2 identified by YA803G2
DEFAULT TABLESPACE  USERS
TEMPORARY TABLESPACE TEMP
QUOTA 1000M ON USERS;--建立使用者


GRANT create session , create table, create view ,create sequence ,create synonym ,create procedure
to YA803G2;--授與物件權限