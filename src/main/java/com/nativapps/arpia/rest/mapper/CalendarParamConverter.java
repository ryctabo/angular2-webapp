package com.nativapps.arpia.rest.mapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ext.ParamConverter;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class CalendarParamConverter implements ParamConverter<Calendar> {

    private static final Logger LOG = Logger.getLogger(CalendarParamConverter.class.getName());

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    
    @Override
    public Calendar fromString(String value) {
         try {
            if (null != value) {
                Calendar date = Calendar.getInstance();
                date.setTime(dateFormat.parse(value));
                return date;
            }
        } catch (ParseException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String toString(Calendar value) {
        return dateFormat.format(value.getTime());
    }
}
