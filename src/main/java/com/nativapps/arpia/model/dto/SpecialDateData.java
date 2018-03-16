package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.model.adapter.DateAdapter;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@XmlRootElement
public class SpecialDateData {

    private String name;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Calendar date;

    public SpecialDateData() {
    }

    public SpecialDateData(String name, Calendar date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}
