package com.nativapps.arpia.rest.mapper;

import com.nativapps.arpia.model.adapter.DateRange;
import javax.ws.rs.ext.ParamConverter;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class DateRangeParamConverter implements ParamConverter<DateRange> {

    @Override
    public DateRange fromString(String value) {
        String[] range = value.split("\\.");

        if (range.length != 2)
            return null;
        try {
            int count = Integer.parseInt(range[0]);
            String unit = range[1].toUpperCase();

            return new DateRange(count, DateRange.Unit.valueOf(unit));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public String toString(DateRange value) {
        return value.toString();
    }

}
