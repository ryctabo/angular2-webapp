package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.CustomerData;
import com.nativapps.arpia.database.entity.CustomerParameter;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class CustomerParamData {

    protected Boolean urgentOrders;

    protected Boolean alwaysMoneyReturn;

    protected String specialRateInfo;

    protected Boolean denied;

    protected String condition;

    protected CustomerTracingData tracing;

    public CustomerParamData() {
    }

    public CustomerParamData(Boolean urgentOrders, Boolean alwaysMoneyReturn,
            String specialRateInfo, Boolean denied, String condition, 
            CustomerTracingData tracing) {
        this.urgentOrders = urgentOrders;
        this.alwaysMoneyReturn = alwaysMoneyReturn;
        this.specialRateInfo = specialRateInfo;
        this.denied = denied;
        this.condition = condition;
        this.tracing = tracing;
    }

    public CustomerParamData(CustomerParameter entity) {
        this.urgentOrders = entity.isUrgentOrders();
        this.alwaysMoneyReturn = entity.isAlwaysMoneyReturn();
        this.specialRateInfo = entity.getSpecialRateInfo();
        this.denied = entity.isDenied();
        this.condition = entity.getCondition();
        this.tracing = new CustomerTracingData(entity.getTracing());
    }

    public Boolean getUrgentOrders() {
        return urgentOrders;
    }

    public void setUrgentOrders(Boolean urgentOrders) {
        this.urgentOrders = urgentOrders;
    }

    public Boolean getAlwaysMoneyReturn() {
        return alwaysMoneyReturn;
    }

    public void setAlwaysMoneyReturn(Boolean alwaysMoneyReturn) {
        this.alwaysMoneyReturn = alwaysMoneyReturn;
    }

    public String getSpecialRateInfo() {
        return specialRateInfo;
    }

    public void setSpecialRateInfo(String specialRateInfo) {
        this.specialRateInfo = specialRateInfo;
    }

    public Boolean getDenied() {
        return denied;
    }

    public void setDenied(Boolean denied) {
        this.denied = denied;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public CustomerTracingData getTracing() {
        return tracing;
    }

    public void setTracing(CustomerTracingData tracing) {
        this.tracing = tracing;
    }
}
