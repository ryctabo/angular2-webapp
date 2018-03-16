package com.nativapps.arpia.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class PersonRequest extends PersonData implements IPersonData {
    
    private List<AddressRequest> addresses;

    private List<EmailRequest> emails;

    private List<PhoneRequest> phones;

    public PersonRequest() {
        this.addresses = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.phones = new ArrayList<>();
    }

    public List<AddressRequest> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressRequest> addresses) {
        this.addresses = addresses;
    }

    public List<EmailRequest> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailRequest> emails) {
        this.emails = emails;
    }

    public List<PhoneRequest> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneRequest> phones) {
        this.phones = phones;
    }
}
