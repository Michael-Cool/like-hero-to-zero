package com.likeherotozero.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    // Constants for roles
    public static final String ROLE_SCIENTIST = "SCIENTIST";
    public static final String ROLE_MODERATOR = "MODERATOR";

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Role Check Methods
    public boolean isScientist() {
        return ROLE_SCIENTIST.equalsIgnoreCase(this.role);
    }

    public boolean isModerator() {
        return ROLE_MODERATOR.equalsIgnoreCase(this.role);
    }
}