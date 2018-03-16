package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftCheckData {
    
    protected Boolean goodAppearance;
    
    protected String observations;

    public Boolean getGoodAppearance() {
        return goodAppearance;
    }

    public void setGoodAppearance(Boolean goodAppearance) {
        this.goodAppearance = goodAppearance;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
