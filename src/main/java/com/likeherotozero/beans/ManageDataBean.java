package com.likeherotozero.beans;

import com.likeherotozero.model.Co2Emission;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
public class ManageDataBean {

    @Inject
    private EntityManager entityManager;

    private Co2Emission newEmission = new Co2Emission();

    public Co2Emission getNewEmission() {
        return newEmission;
    }

    @Transactional
    public void saveNewEmission() {
        try {
            System.out.println("DEBUG: Attempting to save new emission: " +
                "Country=" + newEmission.getCountry() + 
                ", Year=" + newEmission.getYear() + 
                ", EmissionKt=" + newEmission.getEmissionKt() + 
                ", DataSource=" + newEmission.getDataSource());
            
            entityManager.getTransaction().begin(); // Start transaction
            save(newEmission); // Persist or merge the emission
            entityManager.getTransaction().commit(); // Commit transaction
            
            System.out.println("DEBUG: Emission saved successfully.");
            newEmission = new Co2Emission(); // Reset form for new input
        } catch (Exception e) {
            System.err.println("ERROR: Failed to save new emission: " + e.getMessage());
            e.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback(); // Rollback in case of error
            }
        }
    }

    public List<Co2Emission> getEmissions() {
        try {
            return entityManager.createQuery("SELECT e FROM Co2Emission e", Co2Emission.class).getResultList();
        } catch (Exception e) {
            System.err.println("ERROR: Failed to fetch emissions: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void save(Co2Emission emission) {
        try {
            if (emission.getId() == 0) {
                System.out.println("DEBUG: Persisting new emission.");
                entityManager.persist(emission);
            } else {
                System.out.println("DEBUG: Merging existing emission with ID=" + emission.getId());
                entityManager.merge(emission);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to save emission: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void delete(Co2Emission emission) {
        try {
            Co2Emission toDelete = entityManager.find(Co2Emission.class, emission.getId());
            if (toDelete != null) {
                System.out.println("DEBUG: Deleting emission with ID=" + emission.getId());
                entityManager.remove(toDelete);
            } else {
                System.err.println("ERROR: Emission not found for deletion.");
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to delete emission: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Add a countries property
    public List<String> getCountries() {
        return Arrays.asList("USA", "Germany", "France", "China", "India");
    }
}