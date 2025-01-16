package com.likeherotozero.beans;

import com.likeherotozero.service.Co2EmissionService;
import com.likeherotozero.model.Co2Emission;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class IndexBean {
    private String country;
    private List<String> countries; // List of available countries
    private List<Co2Emission> searchResults;

    @Inject
    private Co2EmissionService emissionService;

    @PostConstruct
    public void init() {
        // Fetch the list of countries from the database
        countries = emissionService.getDistinctCountries();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<String> getCountries() {
        return countries;
    }

    public List<Co2Emission> getSearchResults() {
        return searchResults;
    }

    public void search() {
        System.out.println("DEBUG: Searching for country: " + country);

        try {
            searchResults = emissionService.getEmissionsByCountry(country);
            System.out.println("DEBUG: Number of results fetched: " + searchResults.size());
        } catch (Exception e) {
            System.err.println("ERROR: Exception during search: " + e.getMessage());
            e.printStackTrace();
        }
    }
}