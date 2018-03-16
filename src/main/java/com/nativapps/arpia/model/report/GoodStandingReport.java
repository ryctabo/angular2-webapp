package com.nativapps.arpia.model.report;

import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class GoodStandingReport {
    
    private String name;
    
    private String lastName;
    
    private String identification;
    
    private String operationName;
    
    private String createdDate;

    public GoodStandingReport() {
    }

    public GoodStandingReport(String name, String lastName, String identification, 
            String operationName, String createdDate) {
        this.name = name;
        this.lastName = lastName;
        this.identification = identification;
        this.operationName = operationName;
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
