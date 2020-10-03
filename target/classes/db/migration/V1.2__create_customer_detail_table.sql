CREATE TABLE customer_detail (
    id BIGSERIAL        NOT NULL,
    description         VARCHAR(128),
    gender              VARCHAR(16),
    membership          VARCHAR(16),    /*subscriber or not*/
    customer_id         BIGINT
);

ALTER TABLE customer_detail ADD CONSTRAINT customer_detail_pk PRIMARY KEY (id);

ALTER TABLE customer_detail
    ADD CONSTRAINT customer_customer_detail_fk FOREIGN KEY (customer_id)
        REFERENCES customer (id);