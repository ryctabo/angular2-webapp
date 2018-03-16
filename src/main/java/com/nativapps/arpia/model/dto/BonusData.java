package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.model.adapter.DateAdapter;
import java.util.Calendar;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class BonusData {
    
    private Integer countFreeService;

    private String reason;
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Calendar expiryDate;

    public Integer getCountFreeService() {
        return countFreeService;
    }

    public void setCountFreeService(Integer countFreeService) {
        this.countFreeService = countFreeService;
    }
    
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Calendar getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Calendar expiryDate) {
        this.expiryDate = expiryDate;
    }
}
