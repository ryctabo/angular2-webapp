package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class AssignmentData {

    private Integer index;

    private Long messengerId;

    private Calendar registerDate;

    public AssignmentData() {
    }

    public AssignmentData(Integer index, Long messengerId, Calendar registerDate) {
        this.index = index;
        this.messengerId = messengerId;
        this.registerDate = registerDate;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Long messengerId) {
        this.messengerId = messengerId;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

}
