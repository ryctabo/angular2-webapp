package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class DomicileReviewData {

    protected String observations;
    
    protected Long domicileExecuteId;

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Long getDomicileExecuteId() {
        return domicileExecuteId;
    }

    public void setDomicileExecuteId(Long domicileExecuteId) {
        this.domicileExecuteId = domicileExecuteId;
    }
}
