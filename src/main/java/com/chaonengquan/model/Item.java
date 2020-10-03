package com.chaonengquan.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "price")
    private double price;
    //private long orderId;   //for JDBC impl, foreign key column
    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;


    /* --- Getters --- */
    public long getId() { return id; }
    public String getItemName() { return itemName; }
    public double getPrice() { return price; }
    //public long getOrderId() { return orderId; }    //for JDBC impl
    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }


    /* --- Setters --- */
    public void setId(long id) { this.id = id; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setPrice(double price) { this.price = price; }
    //public void setOrderId(long orderId) { this.orderId = orderId; }    //for JDBC impl
    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }


    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                '}';
    }
}
