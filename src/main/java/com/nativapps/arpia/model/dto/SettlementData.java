package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class SettlementData {

    protected Float produced;
    
    protected Float credits;
    
    protected Float advances;
    
    protected Float expenses;

    public Float getProduced() {
        return produced;
    }

    public void setProduced(Float produced) {
        this.produced = produced;
    }

    public Float getCredits() {
        return credits;
    }

    public void setCredits(Float credits) {
        this.credits = credits;
    }

    public Float getAdvances() {
        return advances;
    }

    public void setAdvances(Float advances) {
        this.advances = advances;
    }

    public Float getExpenses() {
        return expenses;
    }

    public void setExpenses(Float expenses) {
        this.expenses = expenses;
    }
}
