package com.likeherotozero.beans;

import com.likeherotozero.model.PendingChange;
import com.likeherotozero.service.ModerationService;

import javax.enterprise.context.RequestScoped;
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
        moderationService.approveChange(changeId); // Directly pass the ID to the service
    }

    public void rejectChange(Integer changeId) {
        moderationService.rejectChange(changeId); // Pass the ID of the change
    }
}