package com.likeherotozero.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(ExampleEntity.class)
                .buildSessionFactory();

        try (Session session = factory.openSession()) {
        	
            session.beginTransaction();


            ExampleEntity example = new ExampleEntity("Hibernate Test Data");

            session.save(example);

            session.getTransaction().commit();

            System.out.println("Inserted ID: " + example.getId());
        } finally {
            factory.close();
        }
    }
}