package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Period;
import com.nativapps.arpia.model.adapter.DateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Calendar;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1
 */
public class FeeInfoData {

    private Long id;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private Calendar firstPayment;

    private Integer numberOfFees;

    private Period period;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getFirstPayment() {
        return firstPayment;
    }

    public void setFirstPayment(Calendar firstPayment) {
        this.firstPayment = firstPayment;
    }

    public Integer getNumberOfFees() {
        return numberOfFees;
    }

    public void setNumberOfFees(Integer numberOfFees) {
        this.numberOfFees = numberOfFees;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
