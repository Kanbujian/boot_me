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

alter table transactions add column deleted_at datetime default null;
alter table logs modify column happend_at datetime;
alter table logs modify column created_at datetime;

alter table transactions add column extra json;

alter table transactions add column notify_url varchar(120);
alter table transactions add column readable_number varchar(32);
alter table transactions add column gateway varchar(32);

create table apps(
  id int primary key auto_increment,
  name varchar(32) not null,
  token varchar(32) not null,
  comment varchar(200),
  created_at datetime,
  updated_at datetime
);