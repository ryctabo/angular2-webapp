package com.nativapps.arpia.rest.bean;

import com.nativapps.arpia.database.entity.DomicileStatus;
import com.nativapps.arpia.database.entity.Gender;

import javax.ws.rs.QueryParam;
import java.util.Calendar;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 3.0
 */
public class DomicileExecuteBean extends GenericFilterBean {

    @QueryParam("state")
    private DomicileStatus state;

    @QueryParam("startDate")
    private Calendar startDate;

    @QueryParam("endDate")
    private Calendar endDate;

    @QueryParam("customer")
    private Long customerId;

    @QueryParam("gender")
    private Gender customerGender;

    @QueryParam("auto")
    private Boolean auto;

    @QueryParam("operator")
    private Long operatorId;

    @QueryParam("dispatcher")
    private Long dispatcherId;

    @QueryParam("operation")
    private Long operationId;

    @QueryParam("canceled")
    private Boolean canceled;

    public DomicileStatus getState() {
        return state;
    }

    public void setState(DomicileStatus state) {
        this.state = state;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Gender getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(Gender customerGender) {
        this.customerGender = customerGender;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getDispatcherId() {
        return dispatcherId;
    }

    public void setDispatcherId(Long dispatcherId) {
        this.dispatcherId = dispatcherId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }
}
