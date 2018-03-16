package com.nativapps.arpia.model.dto;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class ParticularParamResponse extends ParticularParamData {

    public ParticularParamResponse() {
    }

    public ParticularParamResponse(Boolean urgentOrders,
            Boolean alwaysMoneyReturn, String specialRateInfo,
            Long favoriteMessengerId, Boolean denied, String condition,
            CustomerTracingData tracing) {
        super(urgentOrders, alwaysMoneyReturn, specialRateInfo,
                favoriteMessengerId, denied, condition, tracing);
    }
}
