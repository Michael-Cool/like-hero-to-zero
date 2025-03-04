package com.likeherotozero.beans;

import com.likeherotozero.model.Co2Emission;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Named
@RequestScoped
public class AddDataBean {

    private String country;
    private int year;
    private float emissionKt;
    private String dataSource;

    @Inject
    private EntityManager entityManager;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getEmissionKt() {
        return emissionKt;
    }

    public void setEmissionKt(float emissionKt) {
        this.emissionKt = emissionKt;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public void addData() {
        try {
            Co2Emission emission = new Co2Emission();
            emission.setCountry(country);
            emission.setYear(year);
            emission.setEmissionKt(emissionKt);
            emission.setDataSource(dataSource);

            entityManager.persist(emission);
            System.out.println("Daten erfolgreich hinzugefügt.");
        } catch (Exception e) {
            System.err.println("Fehler beim hinzufügen der Daten: " + e.getMessage());
            e.printStackTrace();
        }
    }
}