package com.likeherotozero.model;

public class PendingChangeBuilder {
    private final PendingChange pendingChange;

    public PendingChangeBuilder() {
        this.pendingChange = new PendingChange();
        this.pendingChange.setStatus(PendingChange.Status.PENDING);
    }

    public PendingChangeBuilder withCountry(String country) {
        pendingChange.setCountry(country);
        return this;
    }

    public PendingChangeBuilder withYear(int year) {
        pendingChange.setYear(year);
        return this;
    }

    public PendingChangeBuilder withEmissionKt(double emissionKt) {
        pendingChange.setEmissionKt(emissionKt);
        return this;
    }

    public PendingChangeBuilder withDataSource(String dataSource) {
        pendingChange.setDataSource(dataSource);
        return this;
    }

    public PendingChangeBuilder withStatus(PendingChange.Status status) {
        pendingChange.setStatus(status);
        return this;
    }

    public PendingChangeBuilder withChangeType(PendingChange.ChangeType changeType) {
        pendingChange.setChangeType(changeType);
        return this;
    }

    public PendingChangeBuilder withAffectedId(Integer affectedId) {
        pendingChange.setAffectedId(affectedId);
        return this;
    }

    public PendingChange build() {
        System.out.println("Building PendingChange: " + pendingChange);

        StringBuilder missingFields = new StringBuilder();

        if (pendingChange.getCountry() == null || pendingChange.getCountry().isEmpty()) {
            missingFields.append("country, ");
        }
        if (pendingChange.getYear() <= 0) {
            missingFields.append("year, ");
        }
        if (pendingChange.getEmissionKt() == 0.0) {
            missingFields.append("emissionKt, ");
        }
        if (pendingChange.getDataSource() == null || pendingChange.getDataSource().isEmpty()) {
            missingFields.append("dataSource, ");
        }

        if (missingFields.length() > 0) {
            String errorMessage = "Required fields are missing: " 
                + missingFields.substring(0, missingFields.length() - 2);
            System.out.println(errorMessage);
            throw new IllegalStateException(errorMessage);
        }

        return pendingChange;
    }
}