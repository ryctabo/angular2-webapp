package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Gender;
import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class ParticularData {

    protected String identification;

    protected String name;

    protected String lastName;

    protected Gender gender;

    protected Calendar birthday;

    protected String observations;

    public ParticularData() {
    }

    public ParticularData(String identification, String name, String lastName,
            Gender gender, Calendar birthday, String observations) {
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
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

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

}
