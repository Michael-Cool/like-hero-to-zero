package com.likeherotozero.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.Transactional;

import com.likeherotozero.model.Co2Emission;


@ApplicationScoped
public class Co2EmissionService {

    private EntityManagerFactory emf;

    public Co2EmissionService() {
        this.emf = Persistence.createEntityManagerFactory("like_hero_to_zero");
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Co2Emission> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Co2Emission e", Co2Emission.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Transactional
    public void save(Co2Emission emission) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            if (emission.getId() == 0) {
                em.persist(emission);
            } else {
                em.merge(emission);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    
    public boolean isEntityManagerOpen() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.isOpen();
        } catch (Exception e) {
            System.err.println("ERROR: Unable to check EntityManager state - " + e.getMessage());
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<String> getDistinctCountries() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT e.country FROM Co2Emission e", String.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Co2Emission> getEmissionsByCountry(String country) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Co2Emission e WHERE e.country = :country", Co2Emission.class)
                     .setParameter("country", country)
                     .getResultList();
        } finally {
            em.close();
        }
    }
    
    public void deleteById(int id) {
        EntityManager em = getEntityManager();
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

    @Transactional
    public void delete(Co2Emission emission) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Co2Emission toDelete = em.find(Co2Emission.class, emission.getId());
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