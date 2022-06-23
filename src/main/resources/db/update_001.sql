create table if not exists passport(
  id serial primary key,
  series int,
  number int,
  created timestamp,
  unique (series, number)
);