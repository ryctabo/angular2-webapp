package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.TypeConcept;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1
 */
@XmlRootElement
@XmlDiscriminatorValue("OperatorEgress")
public class OperatorEgressData extends EgressData {

    private TypeConcept typeConcept;

    private Long operatorId;

    private UserResponse operator;

    private FeeInfoData feeInfo;

    public TypeConcept getTypeConcept() {
        return typeConcept;
    }

    public void setTypeConcept(TypeConcept typeConcept) {
        this.typeConcept = typeConcept;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public UserResponse getOperator() {
        return operator;
    }

    public void setOperator(UserResponse operator) {
        this.operator = operator;
    }

    public FeeInfoData getFeeInfo() {
        return feeInfo;
    }

    public void setFeeInfo(FeeInfoData feeInfo) {
        this.feeInfo = feeInfo;
    }
}
