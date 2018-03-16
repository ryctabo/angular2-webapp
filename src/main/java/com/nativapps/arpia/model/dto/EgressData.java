package com.nativapps.arpia.model.dto;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Calendar;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1
 */
@XmlRootElement
@XmlSeeAlso({
        CustomerEgressData.class,
        ManagementEgressData.class,
        MessengerEgressData.class,
        OperatorEgressData.class
})
@XmlDiscriminatorNode("@@type")
public class EgressData {

    private Long id;

    private Double value;

    private String concept;

    private Calendar created;

    private ClosureData closure;

    private Long operationId;

    private OperationResponse operation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public ClosureData getClosure() {
        return closure;
    }

    public void setClosure(ClosureData closure) {
        this.closure = closure;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public OperationResponse getOperation() {
        return operation;
    }

    public void setOperation(OperationResponse operation) {
        this.operation = operation;
    }
}
