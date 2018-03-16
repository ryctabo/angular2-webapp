package com.nativapps.arpia.model.dto;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0.1
 */
@XmlDiscriminatorNode("otype")
public class DomicileExecuteData {

    protected Long discount;

    protected Boolean automatic;

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Boolean getAutomatic() {
        return automatic;
    }

    public void setAutomatic(Boolean automatic) {
        this.automatic = automatic;
    }
}
