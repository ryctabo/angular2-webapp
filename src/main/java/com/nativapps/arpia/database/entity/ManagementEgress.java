package com.nativapps.arpia.database.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
@Entity
@DiscriminatorValue(value = "AE")
public class ManagementEgress extends Egress {

    @Column(name = "IDENTIFICATION", length = 20)
    private String identification;

    @Column(name = "`NAME`", length = 50)
    private String name;

    @Column(name = "LAST_NAME", length = 50)
    private String lastName;

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
