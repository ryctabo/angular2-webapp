package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Cash;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class CashData {

    protected Float amount;
    
    protected String reason;
    
    protected Cash.Type registerType;

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Cash.Type getRegisterType() {
        return registerType;
    }

    public void setRegisterType(Cash.Type registerType) {
        this.registerType = registerType;
    }
}
