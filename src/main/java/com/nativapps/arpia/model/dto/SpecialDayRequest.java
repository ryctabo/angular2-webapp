package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.TypeDay;
import java.util.Calendar;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class SpecialDayRequest extends SpecialDayData {

    public SpecialDayRequest() {
    }

    public SpecialDayRequest(Calendar date, TypeDay typeDay) {
        super(date, typeDay);
    }

}
