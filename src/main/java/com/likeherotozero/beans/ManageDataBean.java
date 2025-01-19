package com.likeherotozero.beans;

import com.likeherotozero.model.Co2Emission;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
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

    public void saveNewEmission() {
        save(newEmission);
        newEmission = new Co2Emission(); // Reset form
    }

    public List<Co2Emission> getEmissions() {
        return entityManager.createQuery("SELECT e FROM Co2Emission e", Co2Emission.class).getResultList();
    }

    public void save(Co2Emission emission) {
        if (emission.getId() == 0) {
            entityManager.persist(emission);
        } else {
            entityManager.merge(emission);
        }
    }

    public void delete(Co2Emission emission) {
        Co2Emission toDelete = entityManager.find(Co2Emission.class, emission.getId());
        if (toDelete != null) {
            entityManager.remove(toDelete);
        }
    }

    // Add a countries property
    public List<String> getCountries() {
        return Arrays.asList("USA", "Germany", "France", "China", "India");
    }
}