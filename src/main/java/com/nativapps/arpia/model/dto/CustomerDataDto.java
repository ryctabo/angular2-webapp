package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.CustomerType;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class CustomerDataDto {

    private Long id;

    private String observations;
    
    private String displayName;
    
    private String identification;
    
    private CustomerType type;
    
    private Boolean banned;

    private CustomerParamData param;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public CustomerParamData getParam() {
        return param;
    }

    public void setParam(CustomerParamData param) {
        this.param = param;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }
}
