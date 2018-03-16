package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class MonitoringResponse extends MonitoringData {

    private Long id;

    private UserResponse user;

    private UserResponse modifierUser;

    private Calendar registerDate;

    private Calendar updatedDate;

    public MonitoringResponse() {
    }

    public MonitoringResponse(Long id, UserResponse user,
            UserResponse modifierUser, Calendar registerDate,
            Calendar updatedDate, Long domicileId, String content) {
        super(domicileId, content);
        this.id = id;
        this.user = user;
        this.modifierUser = modifierUser;
        this.registerDate = registerDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public UserResponse getModifierUser() {
        return modifierUser;
    }

    public void setModifierUser(UserResponse modifierUser) {
        this.modifierUser = modifierUser;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public Calendar getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Calendar updatedDate) {
        this.updatedDate = updatedDate;
    }

}
