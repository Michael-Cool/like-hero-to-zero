package com.likeherotozero.service;

import com.likeherotozero.model.Co2Emission;
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
        try {
            entityManager.getTransaction().begin(); // Start transaction
            entityManager.persist(change); // Save the pending change
            entityManager.getTransaction().commit(); // Commit transaction
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback(); // Rollback on failure
            }
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

    public void approveChange(Integer changeId) {
        try {
            entityManager.getTransaction().begin(); // Start transaction

            PendingChange change = entityManager.find(PendingChange.class, changeId);
            if (change == null) {
                throw new IllegalArgumentException("PendingChange with ID " + changeId + " not found.");
            }

            if (change.getChangeType() == PendingChange.ChangeType.INSERT) {
                // Handle INSERT operation: Add new record to co2_emissions
                Co2Emission newEmission = new Co2Emission();
                newEmission.setCountry(change.getCountry());
                newEmission.setYear(change.getYear());
                newEmission.setEmissionKt((float) change.getEmissionKt());
                newEmission.setDataSource(change.getDataSource());
                entityManager.persist(newEmission);
            } else if (change.getChangeType() == PendingChange.ChangeType.DELETE) {
                // Handle DELETE operation: Remove record from co2_emissions
                Co2Emission existingEmission = entityManager.find(Co2Emission.class, change.getAffectedId());

                if (existingEmission != null) {
                    entityManager.remove(existingEmission); // Delete the record
                } else {
                    throw new IllegalStateException("No matching Co2Emission found for deletion with ID: " + change.getAffectedId());
                }
            }

            // Update the status of the pending change to APPROVED
            change.setStatus(PendingChange.Status.APPROVED);
            entityManager.merge(change);

            entityManager.getTransaction().commit(); // Commit transaction
            System.out.println("PendingChange with ID " + changeId + " approved and processed.");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback(); // Rollback on failure
            }
            System.err.println("Error approving PendingChange: " + e.getMessage());
            throw new IllegalStateException("Failed to approve PendingChange: " + e.getMessage(), e);
        }
    }

    public void rejectChange(Integer changeId) {
        try {
            entityManager.getTransaction().begin(); // Start transaction

            PendingChange change = entityManager.find(PendingChange.class, changeId);
            if (change == null) {
                throw new IllegalArgumentException("PendingChange with ID " + changeId + " not found.");
            }

            change.setStatus(PendingChange.Status.REJECTED); // Update status
            entityManager.merge(change); // Persist changes
            entityManager.getTransaction().commit(); // Commit transaction

            System.out.println("PendingChange with ID " + changeId + " rejected and persisted.");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback(); // Rollback on failure
            }
            System.err.println("Error rejecting PendingChange: " + e.getMessage());
            throw new IllegalStateException("Failed to reject PendingChange: " + e.getMessage(), e);
        }
    }
}