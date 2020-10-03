package com.chaonengquan.model;

import javax.persistence.*;

@Entity
@Table(name = "customer_detail")
public class CustomerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column (name = "description")
    private String description;
    @Column (name = "gender")
    private String gender;
    @Column (name = "membership")
    private String membership;
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /* --- Getters --- */
    public long getId() { return id; }
    public String getDescription() { return description; }
    public String getGender() { return gender; }
    public String getMembership() { return membership; }
    public Customer getCustomer() { return customer; }

    /* --- Setters --- */
    public void setId(long id) { this.id = id; }
    public void setDescription(String description) { this.description = description; }
    public void setGender(String gender) { this.gender = gender; }
    public void setMembership(String membership) { this.membership = membership; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    @Override
    public String toString() {
        return "CustomerDetail{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", gender='" + gender + '\'' +
                ", membership='" + membership + '\'' +
                ", customer=" + customer +
                '}';
    }
}
