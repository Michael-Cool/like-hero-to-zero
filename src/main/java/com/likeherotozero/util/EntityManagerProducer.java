package com.likeherotozero.util;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("like_hero_to_zero");

    @Produces
    public EntityManager produceEntityManager() {
        return emf.createEntityManager();
    }
    
    @PreDestroy
    public void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}