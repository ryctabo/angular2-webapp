package com.nativapps.arpia.rest.mapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ext.ParamConverter;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class DateParamConverter implements ParamConverter<Date> {

    private static final Logger LOG = Logger
            .getLogger(DateParamConverter.class.getName());

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    @Override
    public Date fromString(String value) {
        try {
            if (null != value)
                return dateFormat.parse(value);
        } catch (ParseException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String toString(Date date) {
        return dateFormat.format(date);
    }

}
