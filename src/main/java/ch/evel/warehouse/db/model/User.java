package ch.evel.warehouse.db.model;

import javax.persistence.*;

@Entity
@Table(name = "db_user")
public class User extends EntityModel{

    @Column(length = 40, nullable = false, unique = true)
    private String username;

    @Column(length = 40, unique = true)
    private String password;

    @Column(length = 40)
    private String firstname;

    @Column(length = 40)
    private String lastname;

    @Column(length = 40)
    private String email;

    @Column(length = 40)
    private String address;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private UserRole userRole;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }


}