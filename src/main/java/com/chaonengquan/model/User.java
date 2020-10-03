package com.chaonengquan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(name = "secret_key")
    private String secretKey;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY) //DONT use cascadeType.REMOVE in many to many relationship, it will remove other records also
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name ="user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;
    //Use Set for better performance, List will remove all records then reinsert them back, Hibernate lower level logic


    /*Helper functions*/

    //best practice
    public void addRole(Role role){
        this.getRoles().add(role);
        role.getUsers().add(this);
    }

    //best practice
    public void removeRole(Role role){
        this.getRoles().remove(role);
        role.getUsers().remove(this);
    }


    /*Getters*/
    public long getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getSecretKey() { return secretKey; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public Set<Role> getRoles() {
        if(roles == null){
            roles = new HashSet<>();
        }
        return roles;
    }

    /*Setters*/
    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPassword(String password) {
        this.password = DigestUtils.md5Hex(password.trim());                    //don't store plain text password
    }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email.toLowerCase().trim(); }            //make sure emails are all lowercase
    public void setRoles(Set<Role> roles) { this.roles = roles; }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
