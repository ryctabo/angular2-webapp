package com.nativapps.arpia.model.dto;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorValue;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1
 */
@XmlRootElement
@XmlDiscriminatorValue("CustomerEgress")
public class CustomerEgressData extends EgressData {

    private Long customerId;

    private CustomerDataDto customer;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public CustomerDataDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDataDto customer) {
        this.customer = customer;
    }
}
