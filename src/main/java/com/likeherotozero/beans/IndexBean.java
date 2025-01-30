package com.likeherotozero.beans;

import com.likeherotozero.service.Co2EmissionService;
import com.likeherotozero.model.Co2Emission;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Comparator;

@Named
@RequestScoped
public class IndexBean {

    private String country;
    private List<String> countries;
    private List<Co2Emission> searchResults;

    @Inject
    private Co2EmissionService emissionService;

    @PostConstruct
    public void init() {
        try {
            countries = emissionService.getDistinctCountries();
            System.out.println("DEBUG: Countries loaded: " + countries);
        } catch (Exception e) {
            System.err.println("ERROR: Failed to initialize IndexBean: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void checkLogout(ComponentSystemEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!facesContext.getExternalContext().getFlash().isEmpty()) {
            facesContext.addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sie sind abgemeldet!", null));
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
                searchResults.sort(Comparator.comparing(Co2Emission::getYear).reversed()); // Sort by year (descending)
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