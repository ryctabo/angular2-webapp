package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class DebtResponse extends DebtData {

    private Long id;

    private UserResponse owner;

    private Calendar created;

    public DebtResponse() {
    }

    public DebtResponse(Long id, UserResponse owner, Calendar created,
            Double value, String concept, ClosureData closure,
            FeeInfoData feeInfo) {
        super(value, concept, closure, feeInfo);
        this.id = id;
        this.owner = owner;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserResponse getOwner() {
        return owner;
    }

    public void setOwner(UserResponse owner) {
        this.owner = owner;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

}
