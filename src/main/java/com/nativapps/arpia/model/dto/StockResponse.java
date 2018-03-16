package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class StockResponse extends StockData {

    private Integer index;

    private Calendar registerDate;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }
}
