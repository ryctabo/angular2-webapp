package com.nativapps.arpia.model.dto;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PenaltyData {

    protected String description;

    public PenaltyData() {
    }

    public PenaltyData(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
