package com.likeherotozero.beans;

import com.likeherotozero.model.Co2Emission;
import com.likeherotozero.service.Co2EmissionService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
public class ManageDataBean {

    @Inject
    private Co2EmissionService co2EmissionService;

    private Co2Emission newEmission = new Co2Emission();

    public Co2Emission getNewEmission() {
        return newEmission;
    }

    public void saveNewEmission() {
        try {
            System.out.println("DEBUG: Attempting to save new emission: " +
                "Country=" + newEmission.getCountry() + 
                ", Year=" + newEmission.getYear() + 
                ", EmissionKt=" + newEmission.getEmissionKt() + 
                ", DataSource=" + newEmission.getDataSource());

            co2EmissionService.save(newEmission); // Delegate to service
            
            System.out.println("DEBUG: Emission saved successfully.");
            
            newEmission = new Co2Emission(); // Reset form for new input
            getEmissions(); // Refresh table data
        } catch (Exception e) {
            System.err.println("ERROR: Failed to save new emission: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Co2Emission> getEmissions() {
        try {
            return co2EmissionService.findAll(); // Delegate to service
        } catch (Exception e) {
            System.err.println("ERROR: Failed to fetch emissions: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Co2Emission emission) {
        try {
            co2EmissionService.delete(emission); // Delegate to service
            System.out.println("DEBUG: Emission deleted successfully.");
        } catch (Exception e) {
            System.err.println("ERROR: Failed to delete emission: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        try {
            co2EmissionService.deleteById(id); // Delegate to service
            System.out.println("DEBUG: Emission with ID " + id + " deleted successfully.");
        } catch (Exception e) {
            System.err.println("ERROR: Failed to delete emission with ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<String> getCountries() {
        return Arrays.asList("USA", "Germany", "France", "China", "India");
    }
}