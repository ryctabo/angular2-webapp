package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "GOOD_STANDING")
public class GoodStanding implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "REGISTER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registerDate;
    
    @Column(name = "REFUND_DATE")
    @Temporal(TemporalType.DATE)
    private Calendar refundDate;
    
    @ManyToOne
    @JoinColumn(name = "OPERATION_ID")
    private Operation operation;
    
    @ManyToOne
    @JoinColumn(name = "MESSENGER_ID")
    private Messenger messenger;
    
    @Column(name = "REASON")
    private String reason;
    
    @Column(name = "OBSERVATIONS")
    private String observations;
    
    @Column(name = "MESSAGE")
    private String message;
    
    @Column(name = "STATUS")
    private Boolean approved;
    
    @Column(name = "STATUS_DATE")
    @Temporal(TemporalType.DATE)
    private Calendar approvalDate; 

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public Calendar getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Calendar refundDate) {
        this.refundDate = refundDate;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Calendar getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Calendar approvalDate) {
        this.approvalDate = approvalDate;
    }
}