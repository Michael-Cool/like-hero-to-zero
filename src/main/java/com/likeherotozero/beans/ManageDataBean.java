package com.likeherotozero.beans;

import com.likeherotozero.model.Co2Emission;
import com.likeherotozero.model.PendingChange;
import com.likeherotozero.service.Co2EmissionService;
import com.likeherotozero.service.ModerationService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ManageDataBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private PendingChange newPendingChange;
    private Co2Emission selectedEmission; // Selected row for deletion

    @Inject
    private ModerationService moderationService;

    @Inject
    private Co2EmissionService emissionService;

    @Inject

    public ManageDataBean() {
        resetNewPendingChange();
    }

    public PendingChange getNewPendingChange() {
        return newPendingChange;
    }

    public void setNewPendingChange(PendingChange newPendingChange) {
        this.newPendingChange = newPendingChange;
    }

    public Co2Emission getSelectedEmission() {
        return selectedEmission;
    }

    public void setSelectedEmission(Co2Emission selectedEmission) {
        this.selectedEmission = selectedEmission;
    }

    public List<Co2Emission> getAllEmissions() {
        try {
            return emissionService.findAll(); // Fetch data from the database
        } catch (Exception e) {
            throw new IllegalStateException("Error retrieving emissions: " + e.getMessage(), e);
        }
    }
    
    public void requestDeletion(Co2Emission emission) {
        try {
            if (emission == null) {
                throw new IllegalArgumentException("No emission data provided for deletion.");
            }

            PendingChange deleteRequest = new PendingChange();

            deleteRequest.setCountry(emission.getCountry());
            deleteRequest.setYear(emission.getYear());
            deleteRequest.setEmissionKt(emission.getEmissionKt());
            deleteRequest.setDataSource(emission.getDataSource());
            deleteRequest.setChangeType(PendingChange.ChangeType.DELETE);
            deleteRequest.setStatus(PendingChange.Status.PENDING);
            deleteRequest.setAffectedId(emission.getId()); // Set the ID of the Co2Emission row to be deleted

            moderationService.savePendingChange(deleteRequest);

            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Deletion request submitted for " + emission.getCountry(), null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error submitting deletion request: " + e.getMessage(), null));
            throw new IllegalStateException("Error submitting delete request: " + e.getMessage(), e);
        }
    }
    
    public void saveNewPendingChange() {
        try {
            newPendingChange.setChangeType(PendingChange.ChangeType.INSERT); // Set type as INSERT
            newPendingChange.setStatus(PendingChange.Status.PENDING); // Default status as PENDING

            moderationService.savePendingChange(newPendingChange); // Save to the database
            resetNewPendingChange(); // Reset the form fields

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Your data will be checked!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error submitting data: " + e.getMessage(), null));
            throw new IllegalStateException("Error saving PendingChange: " + e.getMessage(), e);
        }
    }

    private void resetNewPendingChange() {
        newPendingChange = new PendingChange();
        newPendingChange.setStatus(PendingChange.Status.PENDING);
    }
}