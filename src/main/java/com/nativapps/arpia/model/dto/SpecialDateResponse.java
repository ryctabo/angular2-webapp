package com.nativapps.arpia.model.dto;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
@XmlRootElement
public class SpecialDateResponse extends SpecialDateData {

    private Long id;

    public SpecialDateResponse() {
    }

    public SpecialDateResponse(Long id, String name, Calendar date) {
        super(name, date);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
