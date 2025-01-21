package com.likeherotozero.beans;

import com.likeherotozero.model.User;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import javax.persistence.EntityManager;

@Named
@SessionScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private User loggedInUser;

    @Inject
    private EntityManager entityManager;

    // Getters and Setters
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

    public User getLoggedInUser() {
        return loggedInUser;
    }

    // Login Logic
    public String login() {
        try {
            User user = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class)
                .setParameter("username", username)
                .setParameter("password", password) // In production, passwords should be encrypted!
                .getSingleResult();

            if (user != null) {
                loggedInUser = user;
                System.out.println("Login successful for user: " + username);
                return "/pages/index.xhtml?faces-redirect=true"; // Redirect to a secure page
            }
        } catch (Exception e) {
            System.out.println("Login failed for user: " + username);
        }
        return null;
    }

    // Logout Logic
    public String logout() {
        loggedInUser = null; // Clear session
        return "/login.xhtml?faces-redirect=true";
    }

    // Access Control Methods
    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public boolean isScientist() {
        return isLoggedIn() && loggedInUser.isScientist();
    }

    public boolean isModerator() {
        return isLoggedIn() && loggedInUser.isModerator();
    }
    
    public boolean isNotLoggedIn() {
        return !isLoggedIn();
    }
}