package com.likeherotozero.test;

import com.likeherotozero.model.Co2Emission;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("like_hero_to_zero");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("DEBUG: Starting transaction...");
            em.getTransaction().begin();

            // Create a new emission record
            Co2Emission emission = new Co2Emission();
            emission.setCountry("TestCountry");
            emission.setYear(2025);
            emission.setEmissionKt(12345.0f);
            emission.setDataSource("Test Source");

            System.out.println("DEBUG: Persisting emission: " + emission);
            em.persist(emission);

            em.getTransaction().commit();
            System.out.println("DEBUG: Transaction committed successfully!");

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.err.println("DEBUG: Transaction rolled back.");
            }
        } finally {
            em.close();
            emf.close();
            System.out.println("DEBUG: EntityManagerFactory closed.");
        }
    }
}