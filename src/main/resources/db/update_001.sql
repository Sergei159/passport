create table if not exists passport(
  id serial primary key not null,
  series int,
  number int,
  created timestamp,
  unique (series, number)
);