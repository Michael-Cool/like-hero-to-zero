package com.likeherotozero.beans;

import com.likeherotozero.model.PendingChange;
import com.likeherotozero.service.ModerationService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class ManageDataBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private PendingChange newPendingChange = new PendingChange();

    @Inject
    private ModerationService moderationService;

    public PendingChange getNewPendingChange() {
        return newPendingChange;
    }

    public void saveNewPendingChange() {
        try {
            newPendingChange.setStatus(PendingChange.Status.PENDING); // Set status to PENDING
            moderationService.savePendingChange(newPendingChange);   // Save to the pending_changes table
            System.out.println("New change submitted for moderation: " + newPendingChange.getCountry());
            newPendingChange = new PendingChange();                  // Reset the form
        } catch (Exception e) {
            System.err.println("Error submitting new data for moderation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}