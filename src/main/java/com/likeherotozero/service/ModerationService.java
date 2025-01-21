package com.likeherotozero.service;

import com.likeherotozero.model.PendingChange;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ModerationService {

	@Inject
	private EntityManager entityManager;

    @Transactional
    public void savePendingChange(PendingChange change) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(change);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Error saving PendingChange: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalStateException("Error saving PendingChange: " + e.getMessage(), e);
        }
    }

    public List<PendingChange> getAllPendingChanges() {
        return entityManager.createQuery(
            "SELECT p FROM PendingChange p WHERE p.status = :status",
            PendingChange.class
        ).setParameter("status", PendingChange.Status.PENDING).getResultList();
    }

    @Transactional
    public void approveChange(PendingChange change2) {
        PendingChange change = entityManager.find(PendingChange.class, change2);
        if (change != null) {
            change.setStatus(PendingChange.Status.APPROVED);
            entityManager.merge(change);
        } else {
            throw new IllegalArgumentException("PendingChange with ID " + change2 + " not found.");
        }
    }

    @Transactional
    public void rejectChange(PendingChange change2) {
        PendingChange change = entityManager.find(PendingChange.class, change2);
        if (change != null) {
            change.setStatus(PendingChange.Status.REJECTED);
            entityManager.merge(change);
        } else {
            throw new IllegalArgumentException("PendingChange with ID " + change2 + " not found.");
        }
    }
}