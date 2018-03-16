package com.nativapps.arpia.model.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The <strong>FullTimeAdapter</strong> class is used to obtain a more complete date format
 * where it can be obtained up to the time zone of this one.
 * <p>
 * Format: HH:mm:ss.SSSXXX
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class FullTimeAdapter extends XmlAdapter<String, Date> {

    private final DateFormat format = new SimpleDateFormat("HH:mm:ss.SSSXXX");

    @Override
    public Date unmarshal(String value) throws Exception {
        synchronized (format) {
            return format.parse(value);
        }
    }

    @Override
    public String marshal(Date date) throws Exception {
        synchronized (format) {
            return format.format(date);
        }
    }
}
