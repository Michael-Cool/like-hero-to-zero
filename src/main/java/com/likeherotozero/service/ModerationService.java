package com.likeherotozero.service;

import com.likeherotozero.model.PendingChange;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@ApplicationScoped
public class ModerationService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("like_hero_to_zero");

    public List<PendingChange> getAllPendingChanges() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM PendingChange p WHERE p.status = 'PENDING'", PendingChange.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public void approveChange(PendingChange change) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            PendingChange managedChange = em.find(PendingChange.class, change.getId());
            if (managedChange != null) {
                // Move data to co2_emissions
                String insertQuery = "INSERT INTO co2_emissions (country, year, emission_kt, data_source) VALUES (:country, :year, :emissionKt, :dataSource)";
                em.createNativeQuery(insertQuery)
                  .setParameter("country", managedChange.getCountry())
                  .setParameter("year", managedChange.getYear())
                  .setParameter("emissionKt", managedChange.getEmissionKt())
                  .setParameter("dataSource", managedChange.getDataSource())
                  .executeUpdate();

                // Mark as approved
                managedChange.setStatus(PendingChange.Status.APPROVED);
                em.merge(managedChange);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void rejectChange(PendingChange change) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            PendingChange managedChange = em.find(PendingChange.class, change.getId());
            if (managedChange != null) {
                managedChange.setStatus(PendingChange.Status.REJECTED);
                em.merge(managedChange);
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