package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class MarketingMessageData {
    
    protected String observations;
    
    protected Boolean visit;
    
    protected Boolean call;

    public MarketingMessageData() {
    }
    
    public MarketingMessageData(String observations, 
            Boolean visit, Boolean call) {
        this.observations = observations;
        this.visit = visit;
        this.call = call;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Boolean getVisit() {
        return visit;
    }

    public void setVisit(Boolean visit) {
        this.visit = visit;
    }

    public Boolean getCall() {
        return call;
    }

    public void setCall(Boolean call) {
        this.call = call;
    }
}
