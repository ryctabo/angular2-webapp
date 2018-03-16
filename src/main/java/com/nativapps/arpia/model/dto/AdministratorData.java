package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Gender;
import com.nativapps.arpia.model.adapter.DateAdapter;
import java.util.Calendar;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class AdministratorData {

    protected String identification;

    protected String name;

    protected String lastName;

    protected Gender gender;

    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Calendar birthday;

    protected String address;

    protected String email;

    protected String phone;

    protected Boolean linked;

    protected Integer nDayReport;

    protected String observations;

    public AdministratorData() {
    }

    public AdministratorData(String identification, String name, String lastName,
            Gender gender, Calendar birthday, String address, String email,
            String phone, Boolean linked, Integer nDayReport, String observations) {
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.linked = linked;
        this.nDayReport = nDayReport;
        this.observations = observations;
    }

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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getLinked() {
        return linked;
    }

    public void setLinked(Boolean linked) {
        this.linked = linked;
    }

    public Integer getnDayReport() {
        return nDayReport;
    }

    public void setnDayReport(Integer nDayReport) {
        this.nDayReport = nDayReport;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
