package com.likeherotozero.beans;

import com.likeherotozero.model.Co2Emission;
import com.likeherotozero.service.Co2EmissionService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.transaction.Transactional;

@Named
@RequestScoped
public class ScientistDataBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Co2EmissionService emissionService;

    private Co2Emission newEmission = new Co2Emission();

    public Co2Emission getNewEmission() {
        return newEmission;
    }

    public void setNewEmission(Co2Emission newEmission) {
        this.newEmission = newEmission;
    }

    public List<Co2Emission> getEmissions() {
        return emissionService.findAll();
    }

    @Transactional
    public void saveEmission() {
        try {
            System.out.println("DEBUG: Saving emission - Country: " + newEmission.getCountry()
                    + ", Year: " + newEmission.getYear()
                    + ", EmissionKt: " + newEmission.getEmissionKt()
                    + ", DataSource: " + newEmission.getDataSource());
            
            if (newEmission.getId() == 0) {
                // If it's a new emission (no ID), create it
                emissionService.save(newEmission);
                System.out.println("DEBUG: New emission saved successfully.");
            } else {
                // If it's an existing emission, update it
                emissionService.save(newEmission);
                System.out.println("DEBUG: Emission updated successfully.");
            }
            
            newEmission = new Co2Emission();  // Clear the form after saving
        } catch (Exception e) {
            System.err.println("ERROR: Failed to save emission - " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void editEmission(Co2Emission emission) {
        this.newEmission = emission;  // Set the newEmission to the one selected for editing
        System.out.println("DEBUG: Editing emission - Country: " + emission.getCountry()
                + ", Year: " + emission.getYear()
                + ", EmissionKt: " + emission.getEmissionKt()
                + ", DataSource: " + emission.getDataSource());
    }

    public void deleteEmission(Co2Emission emission) {
        try {
            System.out.println("DEBUG: Attempting to delete emission with ID: " + emission.getId());
            emissionService.delete(emission);
            System.out.println("DEBUG: Emission deleted successfully.");
        } catch (Exception e) {
            System.err.println("ERROR: Failed to delete emission - " + e.getMessage());
            e.printStackTrace();
        }
    }
}