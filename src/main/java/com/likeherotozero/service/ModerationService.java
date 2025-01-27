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

            // Find the pending change by ID
            PendingChange change = entityManager.find(PendingChange.class, changeId);
            if (change == null) {
                throw new IllegalArgumentException("PendingChange with ID " + changeId + " not found.");
            }

            // Update the status to APPROVED
            change.setStatus(PendingChange.Status.APPROVED);
            entityManager.merge(change); // Persist the change

            // Insert the approved change into the co2_emissions table
            String insertQuery = "INSERT INTO co2_emissions (country, year, emission_kt, data_source) " +
                                 "VALUES (:country, :year, :emissionKt, :dataSource)";
            entityManager.createNativeQuery(insertQuery)
                         .setParameter("country", change.getCountry())
                         .setParameter("year", change.getYear())
                         .setParameter("emissionKt", change.getEmissionKt())
                         .setParameter("dataSource", change.getDataSource())
                         .executeUpdate();

            entityManager.getTransaction().commit(); // Commit the transaction
            System.out.println("PendingChange with ID " + changeId + " approved and persisted to co2_emissions.");
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