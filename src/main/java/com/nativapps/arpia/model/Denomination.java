package com.nativapps.arpia.model;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class Denomination {

    public enum Type {
        PAPER, COIN
    }
    
    private float value;
    
    private Type cashType;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Type getCashType() {
        return cashType;
    }

    public void setCashType(Type cashType) {
        this.cashType = cashType;
    }
}
