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
public class PersonData {

    protected String identification;

    protected String name;

    protected String lastName;
    
    protected Gender gender;

    @XmlJavaTypeAdapter(DateAdapter.class)
    protected Calendar birthday;

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
}