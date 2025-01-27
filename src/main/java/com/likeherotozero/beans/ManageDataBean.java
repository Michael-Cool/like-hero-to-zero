package com.likeherotozero.beans;

import com.likeherotozero.model.PendingChange;
import com.likeherotozero.service.ModerationService;
import com.likeherotozero.service.UserService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ManageDataBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private PendingChange newPendingChange;
    private PendingChange selectedPendingChange; // For delete action

    @Inject
    private ModerationService moderationService;

    @Inject
    private UserService userService;

    public ManageDataBean() {
        resetNewPendingChange();
    }

    public PendingChange getNewPendingChange() {
        return newPendingChange;
    }

    public void setNewPendingChange(PendingChange newPendingChange) {
        this.newPendingChange = newPendingChange;
    }

    public PendingChange getSelectedPendingChange() {
        return selectedPendingChange;
    }

    public void setSelectedPendingChange(PendingChange selectedPendingChange) {
        this.selectedPendingChange = selectedPendingChange;
    }

    public void saveNewPendingChange() {
        try {
            String currentUser = userService.getCurrentUsername();
            newPendingChange.setSubmittedBy(currentUser);
            newPendingChange.setChangeType(PendingChange.ChangeType.INSERT);

            moderationService.savePendingChange(newPendingChange);
            resetNewPendingChange();
        } catch (Exception e) {
            throw new IllegalStateException("Error saving PendingChange: " + e.getMessage(), e);
        }
    }

    public void submitDeleteRequest() {
        try {
            if (selectedPendingChange == null) {
                throw new IllegalArgumentException("No data selected for deletion.");
            }

            String currentUser = userService.getCurrentUsername();
            selectedPendingChange.setSubmittedBy(currentUser);
            selectedPendingChange.setChangeType(PendingChange.ChangeType.DELETE);
            selectedPendingChange.setStatus(PendingChange.Status.PENDING);

            moderationService.savePendingChange(selectedPendingChange);
        } catch (Exception e) {
            throw new IllegalStateException("Error submitting delete request: " + e.getMessage(), e);
        }
    }

    public List<PendingChange> getPendingChanges() {
        try {
            return moderationService.getAllPendingChanges();
        } catch (Exception e) {
            throw new IllegalStateException("Error retrieving pending changes: " + e.getMessage(), e);
        }
    }

    private void resetNewPendingChange() {
        newPendingChange = new PendingChange();
        newPendingChange.setStatus(PendingChange.Status.PENDING);
    }
}