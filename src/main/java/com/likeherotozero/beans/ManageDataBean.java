package com.likeherotozero.beans;

import com.likeherotozero.model.PendingChange;
import com.likeherotozero.service.ModerationService;
import com.likeherotozero.service.UserService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class ManageDataBean implements Serializable {
    private PendingChange newPendingChange;

    @Inject
    private ModerationService moderationService;

    @Inject
    private UserService userService; // Assuming you have a UserService to get the logged-in user

    public ManageDataBean() {
        resetNewPendingChange();
    }

    public PendingChange getNewPendingChange() {
        return newPendingChange;
    }

    public void setNewPendingChange(PendingChange newPendingChange) {
        this.newPendingChange = newPendingChange;
    }

    public void saveNewPendingChange() {
        try {
            String currentUser = userService.getCurrentUsername(); // Fetch the logged-in user
            newPendingChange.setSubmittedBy(currentUser); // Set the submittedBy field
            newPendingChange.setChangeType(PendingChange.ChangeType.INSERT); // Set the changeType

            moderationService.savePendingChange(newPendingChange); // Save to the database
            resetNewPendingChange(); // Reset for new entry
        } catch (Exception e) {
            throw new IllegalStateException("Error saving PendingChange: " + e.getMessage(), e);
        }
    }

    private void resetNewPendingChange() {
        newPendingChange = new PendingChange();
        newPendingChange.setStatus(PendingChange.Status.PENDING); // Default to PENDING
    }
}