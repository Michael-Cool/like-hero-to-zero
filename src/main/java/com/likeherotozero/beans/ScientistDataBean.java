package com.likeherotozero.beans;

import com.likeherotozero.model.Co2Emission;
import com.likeherotozero.service.Co2EmissionService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.transaction.Transactional;
import javax.faces.view.ViewScoped;

@Named("scientistDataBean")
@ViewScoped
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
            if (newEmission.getId() == 0) {
                emissionService.save(newEmission);
            } else {
                emissionService.save(newEmission);
            }
            
            newEmission = new Co2Emission();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteEmission(Co2Emission emission) {
        try {
            emissionService.delete(emission);
        } catch (Exception e) {
            System.err.println("ERROR: Fehler beim Löschen - " + e.getMessage());
            e.printStackTrace();
        }
    }
}