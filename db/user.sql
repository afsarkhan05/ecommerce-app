create schema user_mgmt;

CREATE TABLE user_mgmt.user_detail (
  id BIGSERIAL PRIMARY KEY,
  userName VARCHAR(20) NOT NULL,
  password VARCHAR(120) NOT NULL,
  email VARCHAR(50) NOT NULL,
  status VARCHAR(20)
);


