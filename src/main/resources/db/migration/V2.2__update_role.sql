
/* Manager */
update roles set allowed_resource = '/allCustomer,/delete' where id = 2;

/* user */
update roles set allowed_resource = '/hello,/customer' where id = 3;