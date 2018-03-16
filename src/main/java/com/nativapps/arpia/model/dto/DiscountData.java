package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.model.adapter.DateAdapter;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Calendar;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
@XmlDiscriminatorNode("otype")
public class DiscountData {

    protected String name;

    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Calendar startDate;

    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Calendar endDate;

    protected Float percent;

    protected Integer useLimit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public Integer getUseLimit() {
        return useLimit;
    }

    public void setUseLimit(Integer useLimit) {
        this.useLimit = useLimit;
    }

}
