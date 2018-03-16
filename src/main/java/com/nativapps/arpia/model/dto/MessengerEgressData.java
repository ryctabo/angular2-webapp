package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.TypeConcept;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1
 */
@XmlRootElement
@XmlDiscriminatorValue("MessengerEgress")
public class MessengerEgressData extends EgressData {

    private TypeConcept typeConcept;

    private Long messengerId;

    private MessengerData messenger;

    private FeeInfoData feeInfo;

    public TypeConcept getTypeConcept() {
        return typeConcept;
    }

    public void setTypeConcept(TypeConcept typeConcept) {
        this.typeConcept = typeConcept;
    }

    public Long getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Long messengerId) {
        this.messengerId = messengerId;
    }

    public MessengerData getMessenger() {
        return messenger;
    }

    public void setMessenger(MessengerData messenger) {
        this.messenger = messenger;
    }

    public FeeInfoData getFeeInfo() {
        return feeInfo;
    }

    public void setFeeInfo(FeeInfoData feeInfo) {
        this.feeInfo = feeInfo;
    }
}
