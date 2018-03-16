package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.DomicileStatus;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0.1
 */
public class DomicileExecuteResponse extends DomicileExecuteData {

    private Long id;

    private UserResponse user;

    private DomicileResponse domicile;

    private DomicileStatus status;

    private List<AssignmentData> assignments;

    private Float priceToPaid;

    private Calendar startDate;

    private Calendar finishDate;

    private Calendar cancelDate;

    private String cancelReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public DomicileResponse getDomicile() {
        return domicile;
    }

    public void setDomicile(DomicileResponse domicile) {
        this.domicile = domicile;
    }

    public DomicileStatus getStatus() {
        return status;
    }

    public void setStatus(DomicileStatus status) {
        this.status = status;
    }

    public List<AssignmentData> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<AssignmentData> assignments) {
        this.assignments = assignments;
    }

    public Float getPriceToPaid() {
        return priceToPaid;
    }

    public void setPriceToPaid(Float priceToPaid) {
        this.priceToPaid = priceToPaid;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Calendar finishDate) {
        this.finishDate = finishDate;
    }

    public Calendar getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Calendar cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }
}
