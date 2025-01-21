package com.likeherotozero.service;

import com.likeherotozero.model.PendingChange;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class ModerationService {

    @Inject
    private EntityManager entityManager;

    public void savePendingChange(PendingChange change) {
        entityManager.getTransaction().begin();
        entityManager.persist(change);
        entityManager.getTransaction().commit();
    }

    public List<PendingChange> getAllPendingChanges() {
        return entityManager.createQuery(
            "SELECT p FROM PendingChange p WHERE p.status = 'PENDING'", 
            PendingChange.class
        ).getResultList();
    }

    public void approveChange(PendingChange change) {
        entityManager.getTransaction().begin();
        PendingChange managedChange = entityManager.find(PendingChange.class, change.getId());
        if (managedChange != null) {
            managedChange.setStatus(PendingChange.Status.APPROVED);
            entityManager.merge(managedChange);
        }
        entityManager.getTransaction().commit();
    }

    public void rejectChange(PendingChange change) {
        entityManager.getTransaction().begin();
        PendingChange managedChange = entityManager.find(PendingChange.class, change.getId());
        if (managedChange != null) {
            managedChange.setStatus(PendingChange.Status.REJECTED);
            entityManager.merge(managedChange);
        }
        entityManager.getTransaction().commit();
    }
}