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

    /**
     * Retrieve all CO2 emissions from the database.
     *
     * @return List of all CO2 emissions.
     */
    public List<Co2Emission> findAll() {
        return entityManager.createQuery("SELECT e FROM Co2Emission e", Co2Emission.class).getResultList();
    }

    /**
     * Retrieve distinct countries from CO2 emissions records.
     *
     * @return List of distinct country names.
     */
    public List<String> getDistinctCountries() {
        return entityManager.createQuery("SELECT DISTINCT e.country FROM Co2Emission e", String.class).getResultList();
    }

    /**
     * Retrieve emissions by country.
     *
     * @param country The country name.
     * @return List of emissions for the specified country.
     */
    public List<Co2Emission> getEmissionsByCountry(String country) {
        return entityManager.createQuery("SELECT e FROM Co2Emission e WHERE e.country = :country", Co2Emission.class)
                            .setParameter("country", country)
                            .getResultList();
    }

    /**
     * Add a new CO2 emission record directly (used by moderator approval).
     *
     * @param emission The CO2 emission record to save.
     */
    @Transactional
    public void save(Co2Emission emission) {
        entityManager.persist(emission);
    }

    /**
     * Delete a CO2 emission record directly (used by moderator action).
     *
     * @param emission The CO2 emission record to delete.
     */
    @Transactional
    public void delete(Co2Emission emission) {
        Co2Emission managedEntity = entityManager.find(Co2Emission.class, emission.getId());
        if (managedEntity != null) {
            entityManager.remove(managedEntity);
        }
    }

    /**
     * Delete a CO2 emission record by its ID (used by moderator action).
     *
     * @param id The ID of the CO2 emission record to delete.
     */
    @Transactional
    public void deleteById(int id) {
        Co2Emission toDelete = entityManager.find(Co2Emission.class, id);
        if (toDelete != null) {
            entityManager.remove(toDelete);
        }
    }
}