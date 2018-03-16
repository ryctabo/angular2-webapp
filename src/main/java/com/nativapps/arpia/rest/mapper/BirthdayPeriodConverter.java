package com.nativapps.arpia.rest.mapper;

import com.nativapps.arpia.model.adapter.BirthdayPeriod;

import javax.ws.rs.ext.ParamConverter;
import java.util.Calendar;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1-SNAPSHOT
 */
public class BirthdayPeriodConverter implements ParamConverter<BirthdayPeriod> {

    @Override
    public BirthdayPeriod fromString(String value) {
        value = value.toLowerCase();
        if (value.matches("today|tomorrow|week|month")) {
            switch (value) {
                case "today":
                    return new BirthdayPeriod();
                case "tomorrow":
                    Calendar tomorrow = Calendar.getInstance();
                    tomorrow.add(Calendar.DAY_OF_YEAR,1 );
                    return new BirthdayPeriod(
                            BirthdayPeriod.Type.TOMORROW,
                            tomorrow,
                            tomorrow);
                case "week":
                    Calendar instance = Calendar.getInstance();
                    instance.add(Calendar.DAY_OF_YEAR, 7);
                    return new BirthdayPeriod(
                            BirthdayPeriod.Type.NEXT_WEEK,
                            Calendar.getInstance(),
                            instance);
                case "month":
                    Calendar start = Calendar.getInstance();
                    start.set(Calendar.DAY_OF_MONTH, 1);
                    Calendar end = Calendar.getInstance();
                    int maxDay = end.getActualMaximum(Calendar.DAY_OF_MONTH);
                    end.set(Calendar.DAY_OF_MONTH, maxDay);
                    return new BirthdayPeriod(BirthdayPeriod.Type.THIS_MONTH, start, end);
            }
        }
        return new BirthdayPeriod();
    }

    @Override
    public String toString(BirthdayPeriod value) {
        return value.toString();
    }

}
