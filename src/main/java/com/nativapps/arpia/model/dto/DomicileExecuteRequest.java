package com.nativapps.arpia.model.dto;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DomicileExecuteRequest extends DomicileExecuteData {

    private Long domicile;

    public Long getDomicile() {
        return domicile;
    }

    public void setDomicile(Long domicile) {
        this.domicile = domicile;
    }

}
