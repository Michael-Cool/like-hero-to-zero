package com.likeherotozero.beans;

import com.likeherotozero.service.ModerationService;
import com.likeherotozero.model.PendingChange;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ModerationBean {

    @Inject
    private ModerationService moderationService;

    public List<PendingChange> getPendingChanges() {
        return moderationService.getAllPendingChanges();
    }

    public void approveChange(PendingChange change) {
        moderationService.approveChange(change);
    }

    public void rejectChange(PendingChange change) {
        moderationService.rejectChange(change);
    }
}