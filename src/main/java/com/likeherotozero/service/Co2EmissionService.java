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

    public List<Co2Emission> findAll() {
        return entityManager.createQuery("SELECT e FROM Co2Emission e", Co2Emission.class).getResultList();
    }

    public List<String> getDistinctCountries() {
        return entityManager.createQuery("SELECT DISTINCT e.country FROM Co2Emission e", String.class).getResultList();
    }

    public List<Co2Emission> getEmissionsByCountry(String country) {
        return entityManager.createQuery("SELECT e FROM Co2Emission e WHERE e.country = :country", Co2Emission.class)
                            .setParameter("country", country)
                            .getResultList();
    }

    @Transactional
    public void save(Co2Emission emission) {
        entityManager.persist(emission);
    }

    @Transactional
    public void delete(Co2Emission emission) {
        Co2Emission managedEntity = entityManager.find(Co2Emission.class, emission.getId());
        if (managedEntity != null) {
            entityManager.remove(managedEntity);
        }
    }

    @Transactional
    public void deleteById(int id) {
        Co2Emission toDelete = entityManager.find(Co2Emission.class, id);
        if (toDelete != null) {
            entityManager.remove(toDelete);
        }
    }
}