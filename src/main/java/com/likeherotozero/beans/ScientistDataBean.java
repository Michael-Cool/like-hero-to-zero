package com.likeherotozero.beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.likeherotozero.model.Co2Emission;

import java.io.Serializable;

@Named // Marks this as a JSF-managed bean
@SessionScoped // Bean lifecycle lasts for the user session
public class ScientistDataBean implements Serializable {

    @PersistenceContext // Injects the JPA EntityManager
    private EntityManager entityManager;

    private Co2Emission newEmission = new Co2Emission(); // Data for a new record

    public Co2Emission getNewEmission() {
        return newEmission;
    }

    public void setNewEmission(Co2Emission newEmission) {
        this.newEmission = newEmission;
    }

    @Transactional // Ensures database operations are part of a transaction
    public void saveEmission() {
        entityManager.persist(newEmission); // Save to database
        newEmission = new Co2Emission(); // Reset after saving
    }
}