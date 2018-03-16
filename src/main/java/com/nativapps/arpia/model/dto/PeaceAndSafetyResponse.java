package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class PeaceAndSafetyResponse extends PeaceAndSafetyData {

    private Calendar create;

    private Long id;

    private MobileNumberResponse mobile;

    public PeaceAndSafetyResponse() {
    }

    public PeaceAndSafetyResponse(Long id, MobileNumberResponse mobile,
            Calendar create, Calendar refund, String groundsDismissal,
            String observations, String message) {
        super(refund, groundsDismissal, observations, message);
        this.create = create;
        this.id = id;
        this.mobile = mobile;
    }

    public MobileNumberResponse getMobile() {
        return mobile;
    }

    public void setMobile(MobileNumberResponse mobile) {
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getCreate() {
        return create;
    }

    public void setCreate(Calendar create) {
        this.create = create;
    }
}
