/*customer table dummy records*/
insert into customer (first_name, last_name, email, address) values
('test_firstname','test_lastname','test@test.com','test_address');
insert into customer (first_name, last_name, email, address) values
('John','Wick','JohnWick@gmail.com','Continental Hotel');
insert into customer (first_name, last_name, email, address) values
('Gandalf','The Grey','Gandalf@middle.earth.com','Unknown');
insert into customer (first_name, last_name, email, address) values
('Alon','Chad','AlonChad@cool.com','Cool place');
insert into customer (first_name, last_name, email, address) values
('Simba','Lion King','Simba@lion.king.com','Africa');


/*order table dummy records*/
insert into customer_order (address, payment, amount, customer_id) values
('test_address', 'test_payment', 99.99, 1);
insert into customer_order (address, payment, amount, customer_id) values
('Tucson', 'VISA', 4.00, 2);
insert into customer_order (address, payment, amount, customer_id) values
('Hobitton', 'Gold Coin', 24.90, 3);
insert into customer_order (address, payment, amount, customer_id) values
('1040 E. 4th Street','Bitcoin', 3.99, 4);
insert into customer_order(address, payment, amount, customer_id) values
('Africa', 'Gift Card', 4.98, 5);


/*item table dummy records*/
insert into item (item_name, price, order_id) values
('Toilet Paper', 99.99, 1);
insert into item (item_name, price, order_id) values
('Soda', 2.00 , 2);
insert into item (item_name, price, order_id) values
('Steak', 15.99 , 3);
insert into item (item_name, price, order_id) values
('Organic Broccoli', 3.99 , 4);
insert into item (item_name, price, order_id) values
('Banana', 2.49 , 5);



