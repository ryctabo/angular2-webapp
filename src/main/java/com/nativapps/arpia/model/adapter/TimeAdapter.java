package com.nativapps.arpia.model.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The <strong>TimeAdapter</strong> class is used to format time where only hour,
 * minute and second are displayed.
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class TimeAdapter extends XmlAdapter<String, Date> {

    private final DateFormat format = new SimpleDateFormat("HH:mm:ss");

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
