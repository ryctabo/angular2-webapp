package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.BaseRecord;
import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class BaseRecordData {

    private Long userId;
    
    private String userDisplayName;

    private BaseRecord.Concept concept;

    private Integer count;

    private Calendar registerDate;
    
    private String observations;

    public BaseRecordData() {
    }

    public BaseRecordData(Long userId, String userDisplayName, 
            BaseRecord.Concept concept, Integer count, Calendar registerDate,
            String observations) {
        this.userId = userId;
        this.userDisplayName = userDisplayName;
        this.concept = concept;
        this.count = count;
        this.registerDate = registerDate;
        this.observations = observations;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BaseRecord.Concept getConcept() {
        return concept;
    }

    public void setConcept(BaseRecord.Concept concept) {
        this.concept = concept;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
