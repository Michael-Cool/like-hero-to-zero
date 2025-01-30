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
    private Co2Emission selectedEmission;

    @Inject
    private ModerationService moderationService;

    @Inject
    private Co2EmissionService emissionService;

    private List<String> allCountries;

    @Inject
    public ManageDataBean() {
        resetNewPendingChange();
    }

    public void init() {
        allCountries = emissionService.getDistinctCountries();
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
        return emissionService.findAll();
    }

    public List<String> getAllCountries() {
        return allCountries;
    }

    public void requestDeletion(Co2Emission emission) {
        try {
            PendingChange deleteRequest = new PendingChange();
            deleteRequest.setCountry(emission.getCountry());
            deleteRequest.setYear(emission.getYear());
            deleteRequest.setEmissionKt(emission.getEmissionKt());
            deleteRequest.setDataSource(emission.getDataSource());
            deleteRequest.setChangeType(PendingChange.ChangeType.DELETE);
            deleteRequest.setStatus(PendingChange.Status.PENDING);
            deleteRequest.setAffectedId(emission.getId());

            moderationService.savePendingChange(deleteRequest);

            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Ihr Auftrag zur Löschung der Daten von " + emission.getCountry() + " wird nun überprüft", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Übertragen der Daten: " + e.getMessage(), null));
        }
    }

    public void saveNewPendingChange() {
        try {
            newPendingChange.setChangeType(PendingChange.ChangeType.INSERT);
            newPendingChange.setStatus(PendingChange.Status.PENDING);

            moderationService.savePendingChange(newPendingChange);
            resetNewPendingChange();

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Ihre Daten werden nun überprüft!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Übertragen der Daten: " + e.getMessage(), null));
        }
    }

    private void resetNewPendingChange() {
        newPendingChange = new PendingChange();
        newPendingChange.setStatus(PendingChange.Status.PENDING);
    }
}