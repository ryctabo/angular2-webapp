package com.nativapps.arpia.model.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The <strong>DateAdapter</strong> class is used to format only date.
 * <p>
 * Format: yyyy-MM-dd
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DateAdapter extends XmlAdapter<String, Calendar> {

    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Calendar unmarshal(String value) throws Exception {
        synchronized (format) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(format.parse(value));
            return instance;
        }
    }

    @Override
    public String marshal(Calendar date) throws Exception {
        synchronized (format) {
            return format.format(date.getTime());
        }
    }

}
