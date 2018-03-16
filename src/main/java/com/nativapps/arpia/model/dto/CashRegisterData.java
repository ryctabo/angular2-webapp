package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.model.Denomination;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class CashRegisterData {

    protected Integer count;
    
    protected Denomination.Type cashType;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Denomination.Type getCashType() {
        return cashType;
    }

    public void setCashType(Denomination.Type cashType) {
        this.cashType = cashType;
    }
}
