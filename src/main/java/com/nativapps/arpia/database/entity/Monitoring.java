package com.nativapps.arpia.database.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "MONITORING")
public class Monitoring implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOMICILE_ID", nullable = false)
    private DomicileExecute domicile;

    @Column(name = "DOMICILE_ID", insertable = false, updatable = false)
    private long domicileId;

    @Column(name = "CONTENT", nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "MODIFIER_USER_ID")
    private User modifierUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REGISTER_DATE", nullable = false, updatable = false)
    private Calendar registerDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Calendar updatedDate;

    public Monitoring() {
        this.registerDate = Calendar.getInstance();
    }

    public Monitoring(DomicileExecute domicile, String content) {
        this.domicile = domicile;
        this.content = content;
        this.registerDate = Calendar.getInstance();
    }

    public Monitoring(long id, DomicileExecute domicile, String content,
            User user) {
        this.id = id;
        this.domicile = domicile;
        this.content = content;
        this.registerDate = Calendar.getInstance();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DomicileExecute getDomicile() {
        return domicile;
    }

    public void setDomicile(DomicileExecute domicile) {
        this.domicile = domicile;
    }

    public long getDomicileId() {
        return domicileId;
    }

    public void setDomicileId(long domicileId) {
        this.domicileId = domicileId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getModifierUser() {
        return modifierUser;
    }

    public void setModifierUser(User modifierUser) {
        this.modifierUser = modifierUser;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public Calendar getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Calendar updatedDate) {
        this.updatedDate = updatedDate;
    }

}
