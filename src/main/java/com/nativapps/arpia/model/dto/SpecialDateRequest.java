package com.nativapps.arpia.model.dto;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@XmlRootElement
public class SpecialDateRequest extends SpecialDateData {

    public SpecialDateRequest() {
    }

    public SpecialDateRequest(String name, Calendar date) {
        super(name, date);
    }

}
