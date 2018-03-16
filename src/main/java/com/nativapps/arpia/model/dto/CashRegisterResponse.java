package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.model.Denomination;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class CashRegisterResponse extends CashRegisterData {

    private Float value;
    
    private Float total;

    public CashRegisterResponse() {
    }
    
    public CashRegisterResponse(Float value, Integer count, 
            Denomination.Type cashType) {
        this.count = count;
        this.value = value;
        this.cashType = cashType;
        this.total = value * count;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
