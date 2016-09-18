Drop table test;
Drop table test2;

CREATE TABLE test(
  pk number primary key,
  name varchar2(1000),
  pic BLOB,
  mime varchar2(100)
);

insert into test (pk, name) values(1, 'Peter' );
insert into test (pk, name) values(2, 'Susan' );
insert into test (pk, name) values(5, 'John' );
insert into test (pk, name) values(6, 'Tom' );


CREATE TABLE test2(
  pk number primary key,
  name varchar2(1000),
  pic1 BLOB,
  pic2 BLOB,
  mime1 varchar2(100),
  mime2 varchar2(100)
);

insert into test2 (pk, name) values(10, 'Peter1' );
insert into test2 (pk, name) values(20, 'Susan1' );
insert into test2 (pk, name) values(50, 'John1' );
insert into test2 (pk, name) values(60, 'Tom1' );
insert into test2 (pk, name) values(90, 'Tom1' );
