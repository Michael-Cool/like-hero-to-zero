package com.likeherotozero.model;

import javax.persistence.*;

@Entity
@Table(name = "co2_emissions")
public class Co2Emission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "emission_kt")
    private float emissionKt;

    @Column(name = "data_source", length = 255)
    private String dataSource;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}