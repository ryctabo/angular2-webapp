package com.nativapps.arpia.model;

import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class SystemConfig {

    private float amount;

    private double securityDeposit;

    private List<Denomination> denominations;

    private List<Question> questions;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public double getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(double securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public List<Denomination> getDenominations() {
        return denominations;
    }

    public void setDenominations(List<Denomination> denominations) {
        this.denominations = denominations;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
