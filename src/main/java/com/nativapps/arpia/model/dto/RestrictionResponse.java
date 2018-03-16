package com.nativapps.arpia.model.dto;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class RestrictionResponse extends RestrictionData {

    private Long id;

    public RestrictionResponse() {
    }

    public RestrictionResponse(Long id, Boolean breakDay,
            Boolean transferBlocked, Boolean serviceBlocked, Float moneyLimit) {
        super(breakDay, transferBlocked, serviceBlocked, moneyLimit);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
