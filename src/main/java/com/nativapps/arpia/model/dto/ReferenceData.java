package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Relationship;
import com.nativapps.arpia.database.entity.TypeReference;
import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0.2
 */
@XmlRootElement
@XmlDiscriminatorNode("otype")
public class ReferenceData {

    private String name;

    private String lastName;

    private String address;

    private String phoneNumber;

    private TypeReference type;

    private Relationship relationship;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public TypeReference getType() {
        return type;
    }

    public void setType(TypeReference type) {
        this.type = type;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

}
