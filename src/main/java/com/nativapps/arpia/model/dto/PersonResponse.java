package com.nativapps.arpia.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class PersonResponse extends PersonData {

    private Long id;
    
    private List<AddressResponse> addresses;

    private List<EmailResponse> emails;

    private List<PhoneResponse> phones;

    public PersonResponse() {
        this.addresses = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.phones = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<AddressResponse> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResponse> addresses) {
        this.addresses = addresses;
    }

    public List<EmailResponse> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailResponse> emails) {
        this.emails = emails;
    }

    public List<PhoneResponse> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneResponse> phones) {
        this.phones = phones;
    }
}
