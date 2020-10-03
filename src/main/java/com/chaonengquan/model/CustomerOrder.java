package com.chaonengquan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer_order")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "address")
    private String address;
    @Column(name = "payment")
    private String payment;
    @Column(name = "amount")
    private double amount;
    //private long customerID;    //for JDBC impl, foreign key column
    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Item> items;


    /* Helper methods */
    /**
     * Connect the relationship between CustomerOrder and Item entity
     * @param item - the 'many' side of this entity relationship
     */
    public void addItem(Item item){
        this.getItems().add(item);
        item.setCustomerOrder(this);
    }


    /* --- Getters --- */
    public long getId() { return id; }
    public String getAddress() { return address; }
    public String getPayment() { return payment; }
    public double getAmount() { return amount; }
   // public long getCustomerID() { return customerID; }  //for JDBC impl
    public Customer getCustomer() { return customer; }
    public Set<Item> getItems() {
        if(this.items == null){
            items = new HashSet<>();
        }
        return items;
    }

    /* --- Setters --- */
    public void setId(long id) { this.id = id; }
    public void setAddress(String address) { this.address = address; }
    public void setPayment(String payment) { this.payment = payment; }
    public void setAmount(double amount) { this.amount = amount; }
    //public void setCustomerID(long customerID) { this.customerID = customerID; }    //for JDBC impl
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setItems(Set<Item> items) {
        this.items = items;
    }


    @Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", payment='" + payment + '\'' +
                ", amount=" + amount +
                '}';
    }
}
