create schema if not exists product_mgmt;

CREATE TABLE product_mgmt.product_detail (
  id integer PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  quantity integer NOT NULL,
  price decimal NOT NULL,
  status VARCHAR(50) NOT NULL
);

insert into product_mgmt.product_detail (id, name, quantity, price, status) values (1, 'iphone', 10, 200.30, 'AVAILABLE');
insert into product_mgmt.product_detail (id, name, quantity, price, status) values (2, 'ipad', 2, 299.9, 'AVAILABLE');
insert into product_mgmt.product_detail (id, name, quantity, price, status) values (3, 'macbook', 4, 1200, 'AVAILABLE');