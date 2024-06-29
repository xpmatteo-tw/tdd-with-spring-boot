
create table carts (
  cart_id varchar(10) not null primary key
);

create table cart_items (
  cart_id varchar(10) not null,
  quantity int not null,
  product_id varchar(10) not null,
  CONSTRAINT fk_carts
        FOREIGN KEY(cart_id)
          REFERENCES carts(cart_id),
  UNIQUE(cart_id, product_id)
);

-- noinspection SqlWithoutWhere
update schema_info set version = 1;
