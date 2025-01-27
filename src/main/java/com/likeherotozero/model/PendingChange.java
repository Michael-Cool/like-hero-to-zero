package com.likeherotozero.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "pending_changes")
public class PendingChange implements Serializable {

    private static final long serialVersionUID = 1L;

	public enum Status {
        PENDING, APPROVED, REJECTED
    }

    public enum ChangeType {
        INSERT, DELETE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "country", nullable = false)
    private String country;

    @NotNull
    @Column(name = "year", nullable = false)
    private int year;

    @NotNull
    @Column(name = "emission_kt", nullable = false)
    private double emissionKt;

    @NotNull
    @Column(name = "data_source", nullable = false)
    private String dataSource;

    @NotNull
    @Column(name = "submitted_by", nullable = false)
    private String submittedBy;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status", nullable = false)
    private Status status = Status.PENDING; // Default to PENDING

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "change_type", nullable = false)
    private ChangeType changeType = ChangeType.INSERT; // Default to INSERT

    @Column(name = "affected_id")
    private Integer affectedId;

    // Default constructor
    public PendingChange() {
        // Default values are already initialized above
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getEmissionKt() {
        return emissionKt;
    }

    public void setEmissionKt(double emissionKt) {
        this.emissionKt = emissionKt;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public Integer getAffectedId() {
        return affectedId;
    }

    public void setAffectedId(Integer affectedId) {
        this.affectedId = affectedId;
    }
}