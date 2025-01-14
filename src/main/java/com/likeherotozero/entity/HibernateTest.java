package com.likeherotozero.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {
    public static void main(String[] args) {
        // Create SessionFactory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml") // Load hibernate.cfg.xml
                .addAnnotatedClass(ExampleEntity.class) // Add the entity class
                .buildSessionFactory();

        // Open a session
        try (Session session = factory.openSession()) {
            // Begin transaction
            session.beginTransaction();

            // Create a new ExampleEntity object
            ExampleEntity example = new ExampleEntity("Hibernate Test Data");

            // Save the object to the database
            session.save(example);

            // Commit the transaction
            session.getTransaction().commit();

            // Print confirmation
            System.out.println("Inserted ID: " + example.getId());
        } finally {
            // Close the factory
            factory.close();
        }
    }
}