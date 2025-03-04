package com.likeherotozero.beans;

import com.likeherotozero.model.PendingChange;
import com.likeherotozero.service.ModerationService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class ModerationBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private ModerationService moderationService;

    public List<PendingChange> getPendingChanges() {
        return moderationService.getAllPendingChanges();
    }

    public void approveChange(Integer changeId) {
        try {
            moderationService.approveChange(changeId);
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Änderung erfolgreich durchgeführt!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler bei der Änderung: " + e.getMessage(), null));
        }
    }

    public void rejectChange(Integer changeId) {
        try {
            moderationService.rejectChange(changeId);
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Die Änderung wurde abgelehnt!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim ablehnen: " + e.getMessage(), null));
        }
    }
}