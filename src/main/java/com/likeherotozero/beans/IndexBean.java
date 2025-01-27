package com.likeherotozero.beans;

import com.likeherotozero.service.Co2EmissionService;
import com.likeherotozero.model.Co2Emission;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    public void checkLogout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.getExternalContext().getFlash().isEmpty()) {
            facesContext.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "You have successfully logged out!", null));
        }
    }
    
    public void init() {
        try {
            // Fetch the list of distinct countries from the database
            countries = emissionService.getDistinctCountries();
            System.out.println("DEBUG: Countries loaded: " + countries);
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load countries: " + e.getMessage());
            e.printStackTrace();
        }
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
        System.out.println("DEBUG: Searching emissions for country: " + country);

        try {
            searchResults = emissionService.getEmissionsByCountry(country);
            if (searchResults != null) {
                System.out.println("DEBUG: Number of results fetched: " + searchResults.size());
            } else {
                System.out.println("DEBUG: No results found for country: " + country);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Exception during search: " + e.getMessage());
            e.printStackTrace();
        }
    }
}