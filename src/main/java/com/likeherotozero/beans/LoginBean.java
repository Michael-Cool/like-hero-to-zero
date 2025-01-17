package com.likeherotozero.beans;

import com.likeherotozero.model.User;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    @Inject
    private EntityManager entityManager; // Inject the EntityManager

    // Getters and setters
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

    // Login method
    public String login() {
        try {
            User user = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class
            ).setParameter("username", username)
             .setParameter("password", password)
             .getSingleResult();

            System.out.println("Login successful for user: " + user.getUsername());
            return "index.xhtml?faces-redirect=true";

        } catch (NoResultException e) {
            System.out.println("Login failed for user: " + username);
            return null; // Stay on the login page
        }
    }
}