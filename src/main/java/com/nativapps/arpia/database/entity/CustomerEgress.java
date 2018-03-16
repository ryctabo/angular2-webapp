package com.nativapps.arpia.database.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
@Entity
@DiscriminatorValue(value = "CE")
public class CustomerEgress extends Egress {

    @OneToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private CustomerData customer;

    public CustomerData getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerData customer) {
        this.customer = customer;
    }
}
