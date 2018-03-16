package com.nativapps.arpia.rest.mapper;

import com.nativapps.arpia.model.adapter.BirthdayPeriod;
import com.nativapps.arpia.model.adapter.DateRange;
import java.lang.reflect.Type;
import java.lang.annotation.Annotation;
import java.util.Calendar;
import java.util.Date;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
@Provider
public class GenericParamProvider implements ParamConverterProvider {

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType,
            Type genericType, Annotation[] annotations) {

        if (Date.class.equals(rawType)) {
            return (ParamConverter<T>) new DateParamConverter();
        } else if (DateRange.class.equals(rawType)) {
            return (ParamConverter<T>) new DateRangeParamConverter();
        } else if (BirthdayPeriod.class.equals(rawType)) {
            return (ParamConverter<T>) new BirthdayPeriodConverter();
        } else if (Calendar.class.equals(rawType)) {
            return (ParamConverter<T>) new CalendarParamConverter();
        }
   
        return null;
    }

}
