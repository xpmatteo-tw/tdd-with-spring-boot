
-- This table is used to record the migration level
-- it is assumed to always have only one row
create table schema_info (
  version int
);

insert into schema_info (version) values (0);