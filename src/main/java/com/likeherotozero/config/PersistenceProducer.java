package com.likeherotozero.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class PersistenceProducer {

    private static final String PERSISTENCE_UNIT_NAME = "like_hero_to_zero"; // Ensure this matches persistence.xml

    private EntityManagerFactory entityManagerFactory;

    public PersistenceProducer() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Produces
    public EntityManagerFactory produceEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Produces
    public EntityManager produceEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}