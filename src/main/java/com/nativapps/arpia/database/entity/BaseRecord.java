package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "BASE_RECORD")
public class BaseRecord implements Serializable {
    
    public enum Concept {
        DELIVERY, RETURN
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "BASE_ID")
    private Base base;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    
    @Column(name = "CONCEPT")
    @Enumerated(EnumType.STRING)
    private Concept concept;
    
    @Column(name = "COUNT")
    private int count;
    
    @Column(name = "REGISTER_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar registerDate;
    
    @Column(name = "OBSERVATIONS", columnDefinition = "TEXT")
    private String observations;

    public BaseRecord() {
    }

    public BaseRecord(User user, Concept concept, int count, 
            Calendar registerDate, String observations) {
        this.user = user;
        this.concept = concept;
        this.count = count;
        this.registerDate = registerDate;
        this.observations = observations;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
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

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
