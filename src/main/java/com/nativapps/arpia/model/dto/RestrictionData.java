package com.nativapps.arpia.model.dto;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class RestrictionData {

    protected Boolean breakDay;

    protected Boolean transferBlocked;

    protected Boolean serviceBlocked;

    protected Float moneyLimit;

    public RestrictionData() {
    }

    public RestrictionData(Boolean breakDay, Boolean transferBlocked,
            Boolean serviceBlocked, Float moneyLimit) {
        this.breakDay = breakDay;
        this.transferBlocked = transferBlocked;
        this.serviceBlocked = serviceBlocked;
        this.moneyLimit = moneyLimit;
    }

    public Boolean getBreakDay() {
        return breakDay;
    }

    public void setBreakDay(Boolean breakDay) {
        this.breakDay = breakDay;
    }

    public Boolean getTransferBlocked() {
        return transferBlocked;
    }

    public void setTransferBlocked(Boolean transferBlocked) {
        this.transferBlocked = transferBlocked;
    }

    public Boolean getServiceBlocked() {
        return serviceBlocked;
    }

    public void setServiceBlocked(Boolean serviceBlocked) {
        this.serviceBlocked = serviceBlocked;
    }

    public Float getMoneyLimit() {
        return moneyLimit;
    }

    public void setMoneyLimit(Float moneyLimit) {
        this.moneyLimit = moneyLimit;
    }

}
