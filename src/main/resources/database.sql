-- docker run --name minesql -p 127.0.0.1:3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7

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

create table logs
(id int primary key auto_increment,
 owner_id int not null,
 owner_type varchar(32) not null,
 event_type varchar(32),
 log_data json,
 happend_at timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 created_at timestamp default CURRENT_TIMESTAMP
);

alter table transaction add column deleted_at timestamp default null;
alter table logs modify column happend_at datetime, created_at datetime;

