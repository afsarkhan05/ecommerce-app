create schema user_mgmt;

CREATE TABLE user_mgmt.user_detail (
  id integer PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

insert into user_mgmt.user_detail (id, name) values (1, 'sherry');
insert into user_mgmt.user_detail (id, name) values (2, 'larry');
insert into user_mgmt.user_detail (id, name) values (3, 'john');


