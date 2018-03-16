package com.nativapps.arpia.model.dto;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
@XmlDiscriminatorNode("otype")
public class DebtData {

    private Double value;

    private String concept;

    private ClosureData closure;

    private FeeInfoData feeInfo;

    public DebtData() {
    }

    public DebtData(Double value, String concept, ClosureData closure,
            FeeInfoData feeInfo) {
        this.value = value;
        this.concept = concept;
        this.closure = closure;
        this.feeInfo = feeInfo;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public ClosureData getClosure() {
        return closure;
    }

    public void setClosure(ClosureData closure) {
        this.closure = closure;
    }

    public FeeInfoData getFeeInfo() {
        return feeInfo;
    }

    public void setFeeInfo(FeeInfoData feeInfo) {
        this.feeInfo = feeInfo;
    }

}
