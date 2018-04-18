-- create transactions table
create table transactions (
  id int primary key auto_increment,
  currency varchar(65), amount int,
  order_action varchar(20)
);

--
insert into transactions
(currency, amount, order_action)
values
('cny', 100, 'charge'),
('cny', 150, 'charge');

alter table transactions add column data json;
alter table transactions change data transaction_data json;