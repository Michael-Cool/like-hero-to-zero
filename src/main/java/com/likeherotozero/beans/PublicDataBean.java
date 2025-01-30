package com.likeherotozero.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.likeherotozero.model.Co2Emission;

import java.util.List;

@Named
@RequestScoped
public class PublicDataBean {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Co2Emission> getLatestEmissions() {
        return entityManager.createQuery(
            "SELECT e FROM Co2Emission e ORDER BY e.year DESC", 
            Co2Emission.class
        ).getResultList();
    }
}