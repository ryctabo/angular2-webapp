package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Month;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class FestiveDayResponse extends FestiveDayData {

    private Long id;

    public FestiveDayResponse() {
    }

    public FestiveDayResponse(Long id, String name, Integer dayOfMonth,
            Month month) {
        super(name, dayOfMonth, month);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
