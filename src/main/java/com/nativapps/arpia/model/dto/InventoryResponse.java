package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0.1
 */
public class InventoryResponse extends InventoryData {

    private Long id;

    private Calendar registerDate;

    private Calendar updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public Calendar getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Calendar updatedDate) {
        this.updatedDate = updatedDate;
    }
}
