package com.nativapps.arpia.model.dto;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class RestrictionRequest extends RestrictionData {

    public RestrictionRequest() {
    }

    public RestrictionRequest(Boolean breakDay, Boolean transferBlocked,
            Boolean serviceBlocked, Float moneyLimit) {
        super(breakDay, transferBlocked, serviceBlocked, moneyLimit);
    }

}
