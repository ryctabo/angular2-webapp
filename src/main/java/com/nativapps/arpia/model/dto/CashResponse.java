package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class CashResponse extends CashData {
    
    private Long id;
    
    private OperationResponse operation;
    
    private UserResponse user;
    
    private Calendar registerDate;
    
    private float totalCash;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationResponse getOperation() {
        return operation;
    }

    public void setOperation(OperationResponse operation) {
        this.operation = operation;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public float getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(float totalCash) {
        this.totalCash = totalCash;
    }
}
