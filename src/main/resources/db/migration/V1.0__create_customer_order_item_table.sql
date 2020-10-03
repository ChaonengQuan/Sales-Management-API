/*Create three one-to-many relation tables.
 A customer can have many orders, a order can have many items*/

CREATE TABLE customer(
    id                  BIGSERIAL NOT NULL,
    first_name          VARCHAR(32),
    last_name           VARCHAR(32),
    email               VARCHAR(128),
    address             VARCHAR(128)
    /*could add more attributes in customer details table*/
);
ALTER TABLE customer ADD CONSTRAINT customer_pk PRIMARY KEY ( id );


CREATE TABLE customer_order (
    id					BIGSERIAL NOT NULL,
	address	            VARCHAR(256) NOT NULL,
	payment             VARCHAR(128) NOT NULL,
	amount              DOUBLE PRECISION NOT NULL,
	customer_id    		BIGINT NOT NULL
	/*could add more attributes in order details table*/
);
ALTER TABLE customer_order  ADD CONSTRAINT customer_order_pk PRIMARY KEY ( id );


CREATE TABLE item (
    id            	    BIGSERIAL NOT NULL,
    item_name           VARCHAR(128),
	price			    DOUBLE PRECISION NOT NULL,
	order_id    	    BIGINT NOT NULL
	/*could add more attributes in item table*/
);
ALTER TABLE item ADD CONSTRAINT item_pk PRIMARY KEY ( id );


/*add foreign key in the item table*/
ALTER TABLE item
    ADD CONSTRAINT item_order_fk FOREIGN KEY ( order_id )
        REFERENCES customer_order ( id );


/*add foreign key in the orders table*/
ALTER TABLE customer_order
    ADD CONSTRAINT order_customer_fk FOREIGN KEY ( customer_id )
        REFERENCES customer ( id );