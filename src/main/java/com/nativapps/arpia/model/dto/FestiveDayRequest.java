package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Month;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class FestiveDayRequest extends FestiveDayData {

    public FestiveDayRequest() {
    }

    public FestiveDayRequest(String name, Integer dayOfMonth, Month month) {
        super(name, dayOfMonth, month);
    }

}
