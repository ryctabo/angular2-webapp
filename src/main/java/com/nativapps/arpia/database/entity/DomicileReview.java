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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "DOMICILE_REVIEW")
public class DomicileReview implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "OBSERVATIONS", columnDefinition = "TEXT")
    private String observations;
    
    @OneToOne
    @JoinColumn(name = "DOMICILE_EXE_ID", nullable = false)
    private DomicileExecute domicileExecute;
    
    @Column(name = "DOMICILE_EXE_ID", insertable = false, updatable = false)
    private long domicileExecuteId;
    
    @Column(name = "REGISTER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registerDate;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public DomicileExecute getDomicileExecute() {
        return domicileExecute;
    }

    public void setDomicileExecute(DomicileExecute domicileExecute) {
        this.domicileExecute = domicileExecute;
    }

    public long getDomicileExecuteId() {
        return domicileExecuteId;
    }

    public void setDomicileExecuteId(long domicileExecuteId) {
        this.domicileExecuteId = domicileExecuteId;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
