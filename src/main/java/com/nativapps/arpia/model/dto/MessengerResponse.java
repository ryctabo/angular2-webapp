package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Gender;
import java.util.Calendar;
import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.0
 */
public class MessengerResponse extends MessengerData {

    private Long id;

    private List<AddressResponse> addresses;

    private List<EmailResponse> emails;

    private List<PhoneResponse> phones;

    private Long curriculumVitaeId;

    private Long userId;

    private Integer mobile;
    
    private Boolean retire;

    public MessengerResponse() {
    }

    public MessengerResponse(Long id, List<AddressResponse> addresses,
            List<EmailResponse> emails, List<PhoneResponse> phones,
            Long curriculumVitaeId, Long userId, Integer mobile,
            String identification, String name, String lastName, Gender gender,
            Calendar birthday, Boolean dismissal, String observations,
            String photo, Integer category) {
        super(identification, name, lastName, gender, birthday, dismissal,
                observations, photo, category);
        this.id = id;
        this.addresses = addresses;
        this.emails = emails;
        this.phones = phones;
        this.curriculumVitaeId = curriculumVitaeId;
        this.userId = userId;
        this.mobile = mobile;
    }

    public MessengerResponse(Long id, String identification, String name, String lastName, 
            Gender gender, Calendar birthday) {
        super(identification, name, lastName, gender, birthday);
        this.id = id;
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

    public Long getCurriculumVitaeId() {
        return curriculumVitaeId;
    }

    public void setCurriculumVitaeId(Long curriculumVitaeId) {
        this.curriculumVitaeId = curriculumVitaeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }
    
    public Boolean getRetire() {
        return retire;
    }

    public void setRetire(Boolean retire) {
        this.retire = retire;
    }
}
