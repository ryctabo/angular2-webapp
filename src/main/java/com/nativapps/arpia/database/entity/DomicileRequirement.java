package com.nativapps.arpia.database.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@Entity
@Table(name = "HOME_SERVICE_REQUERIMENTS")
public class DomicileRequirement implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "CASH_REQUIRED", nullable = false)
    private float cashRequired;

    @Column(name = "MONITORING", nullable = false)
    private boolean monitoring;

    @Column(name = "ENRUTABLE", nullable = false)
    private boolean enrutable;

    @Column(name = "HAMPER", nullable = false)
    private boolean hamper;

    @Column(name = "PRICE_SMART", nullable = false)
    private boolean priceSmart;

    @Column(name = "WINE_CELLAR", nullable = false)
    private boolean wineCellar;

    @Column(name = "IDENTIFICATION", nullable = false)
    private boolean identification;

    @Column(name = "EASY_CHECK", nullable = false)
    private boolean easyCheck;

    @Column(name = "TICKET", nullable = false)
    private boolean ticket;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getCashRequired() {
        return cashRequired;
    }

    public void setCashRequired(float cashRequired) {
        this.cashRequired = cashRequired;
    }

    public boolean isMonitoring() {
        return monitoring;
    }

    public void setMonitoring(boolean monitoring) {
        this.monitoring = monitoring;
    }

    public boolean isEnrutable() {
        return enrutable;
    }

    public void setEnrutable(boolean enrutable) {
        this.enrutable = enrutable;
    }

    public boolean isHamper() {
        return hamper;
    }

    public void setHamper(boolean hamper) {
        this.hamper = hamper;
    }

    public boolean isPriceSmart() {
        return priceSmart;
    }

    public void setPriceSmart(boolean priceSmart) {
        this.priceSmart = priceSmart;
    }

    public boolean isWineCellar() {
        return wineCellar;
    }

    public void setWineCellar(boolean wineCellar) {
        this.wineCellar = wineCellar;
    }

    public boolean isIdentification() {
        return identification;
    }

    public void setIdentification(boolean identification) {
        this.identification = identification;
    }

    public boolean isEasyCheck() {
        return easyCheck;
    }

    public void setEasyCheck(boolean easyCheck) {
        this.easyCheck = easyCheck;
    }

    public boolean isTicket() {
        return ticket;
    }

    public void setTicket(boolean ticket) {
        this.ticket = ticket;
    }
}
