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
            entityManager.getTransaction().begin();
            entityManager.persist(change);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Fehler beim Speichern: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalStateException("Fehler beim Speichern: " + e.getMessage(), e);
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
            entityManager.getTransaction().begin();

            PendingChange change = entityManager.find(PendingChange.class, changeId);
            if (change == null) {
                throw new IllegalArgumentException("PendingChange with ID " + changeId + " not found.");
            }

            if (change.getChangeType() == PendingChange.ChangeType.INSERT) {
                Co2Emission newEmission = new Co2Emission();
                newEmission.setCountry(change.getCountry());
                newEmission.setYear(change.getYear());
                newEmission.setEmissionKt((float) change.getEmissionKt());
                newEmission.setDataSource(change.getDataSource());
                entityManager.persist(newEmission);
            } else if (change.getChangeType() == PendingChange.ChangeType.DELETE) {
                Co2Emission existingEmission = entityManager.find(Co2Emission.class, change.getAffectedId());

                if (existingEmission != null) {
                    entityManager.remove(existingEmission);
                } else {
                    throw new IllegalStateException("Keine passende ID gefunden: " + change.getAffectedId());
                }
            }

            change.setStatus(PendingChange.Status.APPROVED);
            entityManager.merge(change);

            entityManager.getTransaction().commit();
            System.out.println("Änderung mit ID " + changeId + " angenommen.");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Fehler bei der Bestätigung: " + e.getMessage());
            throw new IllegalStateException("Fehler bei der Bestätigung: " + e.getMessage(), e);
        }
    }

    public void rejectChange(Integer changeId) {
        try {
            entityManager.getTransaction().begin();

            PendingChange change = entityManager.find(PendingChange.class, changeId);
            if (change == null) {
                throw new IllegalArgumentException("Änderung mit ID " + changeId + " nicht gefunden.");
            }

            change.setStatus(PendingChange.Status.REJECTED);
            entityManager.merge(change);
            entityManager.getTransaction().commit();

            System.out.println("Änderung mit ID " + changeId + " abgewiesen.");
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            System.err.println("Error rejecting PendingChange: " + e.getMessage());
            throw new IllegalStateException("Failed to reject PendingChange: " + e.getMessage(), e);
        }
    }
}