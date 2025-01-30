package com.likeherotozero;

import com.likeherotozero.config.PersistenceProducer;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    @Inject
    private PersistenceProducer persistenceProducer;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (persistenceProducer != null) {
            persistenceProducer.closeEntityManagerFactory();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }
}