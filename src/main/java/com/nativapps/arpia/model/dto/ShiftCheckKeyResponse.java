package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftCheckKeyResponse {

    private String key;

    private Calendar creationDate;

    private Calendar useDate;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public Calendar getUseDate() {
        return useDate;
    }

    public void setUseDate(Calendar useDate) {
        this.useDate = useDate;
    }
}
