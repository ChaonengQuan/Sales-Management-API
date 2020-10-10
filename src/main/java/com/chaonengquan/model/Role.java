package com.chaonengquan.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "allowed_resource")
    private String allowedResource;
    @Column(name = "allowed_read")
    private boolean allowedRead;
    @Column(name = "allowed_create")
    private boolean allowedCreate;
    @Column(name = "allowed_update")
    private boolean allowedUpdate;
    @Column(name = "allowed_delete")
    private boolean allowedDelete;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)     //if eager type, use JsonIgnore
    private Set<User> users;


    /*Helper functions*/

    //best practice
    public void addUser(User user){
        this.getUsers().add(user);
        user.getRoles().add(this);
    }

    //best practice
    public void removeUser(User user){
        this.getUsers().remove(user);
        user.getRoles().remove(this);
    }


    /*Getters*/
    public long getId() { return id; }
    public String getName() { return name; }
    public String getAllowedResource() { return allowedResource; }
    public boolean isAllowedRead() { return allowedRead; }
    public boolean isAllowedCreate() { return allowedCreate; }
    public boolean isAllowedUpdate() { return allowedUpdate; }
    public boolean isAllowedDelete() { return allowedDelete; }
    public Set<User> getUsers() {
        if(users == null){
            users = new HashSet<>();
        }
        return users;
    }


    /*Setters*/
    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAllowedResource(String allowedResource) { this.allowedResource = allowedResource; }
    public void setAllowedRead(boolean allowedRead) { this.allowedRead = allowedRead; }
    public void setAllowedCreate(boolean allowedCreate) { this.allowedCreate = allowedCreate; }
    public void setAllowedUpdate(boolean allowedUpdate) { this.allowedUpdate = allowedUpdate; }
    public void setAllowedDelete(boolean allowedDelete) { this.allowedDelete = allowedDelete; }
    public void setUsers(Set<User> users) { this.users = users; }


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", allowedResource='" + allowedResource + '\'' +
                ", allowedRead=" + allowedRead +
                ", allowedCreate=" + allowedCreate +
                ", allowedUpdate=" + allowedUpdate +
                ", allowedDelete=" + allowedDelete +
                '}';
    }
}
