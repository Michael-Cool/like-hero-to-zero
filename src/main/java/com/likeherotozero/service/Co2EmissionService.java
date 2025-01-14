package com.likeherotozero.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import com.likeherotozero.model.Co2Emission;

@ApplicationScoped
public class Co2EmissionService {

    @Inject
    private EntityManager entityManager;

    // Method to retrieve all CO2 emissions
    public List<Co2Emission> getAllEmissions() {
        return entityManager.createQuery("SELECT e FROM Co2Emission e", Co2Emission.class).getResultList();
    }

    // Method to retrieve emissions by country
    public List<Co2Emission> getEmissionsByCountry(String country) {
        return entityManager.createQuery(
            "SELECT e FROM Co2Emission e WHERE e.country = :country", 
            Co2Emission.class
        ).setParameter("country", country).getResultList();
    }

    // Method to add a new CO2 emission
    public void addEmission(Co2Emission emission) {
        entityManager.getTransaction().begin();
        entityManager.persist(emission);
        entityManager.getTransaction().commit();
    }
}