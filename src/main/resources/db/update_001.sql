create table if not exists passport(
  id serial primary key,
  series int,
  number int,
  expired timestamp,
  unique (series, number)
);