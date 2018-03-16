package com.nativapps.arpia.model.dto;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */

public class DebtRequest extends DebtData {

    private Long ownerId;

    public DebtRequest() {
    }

    public DebtRequest(Long ownerId, Double value, String concept,
            ClosureData closure, FeeInfoData feeInfo) {
        super(value, concept, closure, feeInfo);
        this.ownerId = ownerId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

}
