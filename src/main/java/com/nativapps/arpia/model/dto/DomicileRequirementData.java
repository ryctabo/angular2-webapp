package com.nativapps.arpia.model.dto;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DomicileRequirementData {

    private Float cashRequired;

    private Boolean monitoring;

    private Boolean enrutable;

    private Boolean hamper;

    private Boolean priceSmart;

    private Boolean wineCellar;

    private Boolean identification;

    private Boolean easyCheck;

    private Boolean ticket;

    public Float getCashRequired() {
        return cashRequired;
    }

    public void setCashRequired(Float cashRequired) {
        this.cashRequired = cashRequired;
    }

    public Boolean getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(Boolean monitoring) {
        this.monitoring = monitoring;
    }

    public Boolean getEnrutable() {
        return enrutable;
    }

    public void setEnrutable(Boolean enrutable) {
        this.enrutable = enrutable;
    }

    public Boolean getHamper() {
        return hamper;
    }

    public void setHamper(Boolean hamper) {
        this.hamper = hamper;
    }

    public Boolean getPriceSmart() {
        return priceSmart;
    }

    public void setPriceSmart(Boolean priceSmart) {
        this.priceSmart = priceSmart;
    }

    public Boolean getWineCellar() {
        return wineCellar;
    }

    public void setWineCellar(Boolean wineCellar) {
        this.wineCellar = wineCellar;
    }

    public Boolean getIdentification() {
        return identification;
    }

    public void setIdentification(Boolean identification) {
        this.identification = identification;
    }

    public Boolean getEasyCheck() {
        return easyCheck;
    }

    public void setEasyCheck(Boolean easyCheck) {
        this.easyCheck = easyCheck;
    }

    public Boolean getTicket() {
        return ticket;
    }

    public void setTicket(Boolean ticket) {
        this.ticket = ticket;
    }
}
