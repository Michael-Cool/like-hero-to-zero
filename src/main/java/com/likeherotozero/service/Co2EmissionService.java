package com.likeherotozero.service;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import com.likeherotozero.model.Co2Emission;
import javax.persistence.Persistence;

@ApplicationScoped
public class Co2EmissionService {

	 private EntityManagerFactory emf = Persistence.createEntityManagerFactory("like_hero_to_zero");

    /**
     * Retrieve all CO2 emissions from the database.
     *
     * @return List of all CO2 emissions.
     */
	    public List<Co2Emission> findAll() {
	        EntityManager em = emf.createEntityManager();
	        try {
	            return em.createQuery("SELECT e FROM Co2Emission e", Co2Emission.class).getResultList();
	        } finally {
	            em.close();
	        }
	    }

    /**
     * Save or update a CO2 emission record in the database.
     *
     * @param emission The CO2 emission record to save or update.
     */
	    @Transactional
	    public void save(Co2Emission emission) {
	        EntityManager em = emf.createEntityManager();
	        try {
	            em.getTransaction().begin();
	            if (emission.getId() == 0) {
	                em.persist(emission); // New record
	            } else {
	                em.merge(emission); // Update existing record
	            }
	            em.getTransaction().commit();
	        } catch (Exception e) {
	            em.getTransaction().rollback();
	            throw e;
	        } finally {
	            em.close();
	        }
	    }

    /**
     * Retrieve distinct countries from CO2 emissions records.
     *
     * @return List of distinct country names.
     */
	    public List<String> getDistinctCountries() {
	        EntityManager em = emf.createEntityManager();
	        try {
	            return em.createQuery("SELECT DISTINCT e.country FROM Co2Emission e", String.class).getResultList();
	        } finally {
	            em.close();
	        }
	    }

	    public List<Co2Emission> getEmissionsByCountry(String country) {
	        EntityManager em = emf.createEntityManager();
	        try {
	            return em.createQuery("SELECT e FROM Co2Emission e WHERE e.country = :country", Co2Emission.class)
	                     .setParameter("country", country)
	                     .getResultList();
	        } finally {
	            em.close();
	        }
	    }

    /**
     * Delete a specific CO2 emission record.
     *
     * @param emission The CO2 emission record to delete.
     */
    @Transactional
    public void delete(Co2Emission emission) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Co2Emission managedEntity = em.find(Co2Emission.class, emission.getId());
            if (managedEntity != null) {
                em.remove(managedEntity);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Delete a CO2 emission record by its ID.
     *
     * @param id The ID of the CO2 emission record to delete.
     */
    @Transactional
    public void deleteById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Co2Emission toDelete = em.find(Co2Emission.class, id);
            if (toDelete != null) {
                em.remove(toDelete);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}