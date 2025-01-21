package com.likeherotozero.service;

import com.likeherotozero.model.Co2Emission;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class Co2EmissionService {

    @Inject
    private EntityManager entityManager;

    @Transactional
    public void save(Co2Emission emission) {
        if (emission.getId() == 0) {
            entityManager.persist(emission);
            System.out.println("DEBUG: New emission persisted.");
        } else {
            entityManager.merge(emission);
            System.out.println("DEBUG: Existing emission updated.");
        }
    }

    public List<Co2Emission> findAll() {
        return entityManager.createQuery("SELECT e FROM Co2Emission e", Co2Emission.class).getResultList();
    }

    @Transactional
    public void delete(Co2Emission emission) {
        Co2Emission toDelete = entityManager.find(Co2Emission.class, emission.getId());
        if (toDelete != null) {
            entityManager.remove(toDelete);
            System.out.println("DEBUG: Emission deleted successfully.");
        } else {
            System.err.println("ERROR: Emission not found for deletion.");
        }
    }

    @Transactional
    public void deleteById(int id) {
        Co2Emission toDelete = entityManager.find(Co2Emission.class, id);
        if (toDelete != null) {
            entityManager.remove(toDelete);
            System.out.println("DEBUG: Emission with ID " + id + " deleted successfully.");
        } else {
            System.err.println("ERROR: No emission found with ID " + id + " for deletion.");
        }
    }

    public List<String> getDistinctCountries() {
        try {
            return entityManager.createQuery("SELECT DISTINCT e.country FROM Co2Emission e", String.class).getResultList();
        } catch (Exception e) {
            System.err.println("ERROR: Failed to fetch distinct countries: " + e.getMessage());
            throw e;
        }
    }

    public List<Co2Emission> getEmissionsByCountry(String country) {
        try {
            return entityManager.createQuery("SELECT e FROM Co2Emission e WHERE e.country = :country", Co2Emission.class)
                    .setParameter("country", country)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("ERROR: Failed to fetch emissions for country: " + country + " - " + e.getMessage());
            throw e;
        }
    }
}