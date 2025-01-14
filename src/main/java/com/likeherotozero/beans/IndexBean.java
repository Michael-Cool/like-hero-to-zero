package com.likeherotozero.beans;

import com.likeherotozero.model.Co2Emission;
import com.likeherotozero.service.Co2EmissionService;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import javax.persistence.EntityManager;

@Named
@RequestScoped
public class IndexBean {
    private String country;
    private List<Co2Emission> searchResults;

    // Inject the Co2EmissionService
    @Inject
    private Co2EmissionService emissionService;
    private EntityManager entityManager;

    // Getter and Setter for 'country'
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // Getter for searchResults
    public List<Co2Emission> getSearchResults() {
        return searchResults;
    }

    // Search method
    public void search() {
        System.out.println("Searching for country: " + country); // Log the country being searched

        try {
            searchResults = entityManager.createQuery(
                "SELECT e FROM Co2Emission e WHERE e.country = :country",
                Co2Emission.class
            ).setParameter("country", country).getResultList();

            System.out.println("Search results size: " + searchResults.size()); // Log number of results found

            if (!searchResults.isEmpty()) {
                searchResults.forEach(result -> {
                    System.out.println("Result: " + result.getCountry() + ", Year: " + result.getYear());
                });
            } else {
                System.out.println("No results found for country: " + country);
            }
        } catch (Exception e) {
            System.err.println("Error during search: " + e.getMessage()); // Log any errors
            e.printStackTrace();
        }
    }

    // Fetch all emissions from the database (for testing purposes)
    public void fetchAllEmissions() {
        searchResults = emissionService.getAllEmissions();
    }
}