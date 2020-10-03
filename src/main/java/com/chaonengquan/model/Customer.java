package com.chaonengquan.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    //the difference between LAZY and EAGER, eager will also carry orders information, lazy will not
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CustomerOrder> customerOrders;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CustomerDetail customerDetail;


    /* Helper methods */
    /**
     * Connect the relationship between Customer and CustomerOrder entity
     * @param customerOrder - the 'many' side of this entity relationship
     */
    public void addCustomerOrder(CustomerOrder customerOrder){
        this.getCustomerOrders().add(customerOrder);
        customerOrder.setCustomer(this);
    }


    /* --- Getters --- */
    public long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public Set<CustomerOrder> getCustomerOrders() {
        if(customerOrders == null){
            customerOrders = new HashSet<>();
        }
        return customerOrders;
    }
    public CustomerDetail getCustomerDetail() { return customerDetail; }

    /* --- Setters --- */
    public void setId(long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setCustomerOrders(Set<CustomerOrder> customerOrders) { this.customerOrders = customerOrders; }
    public void setCustomerDetail(CustomerDetail customerDetail) { this.customerDetail = customerDetail; }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
