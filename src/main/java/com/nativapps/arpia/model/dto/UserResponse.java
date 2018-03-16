package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.UserType;
import com.nativapps.arpia.rest.UserInfo;

import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class UserResponse extends UserData implements UserInfo {

    private Long id;

    private Boolean enabled;

    private Calendar created;

    private Calendar updated;

    public UserResponse() {
    }

    public UserResponse(Long id, Boolean enabled, Calendar created,
            Calendar updated, String username, String email, String displayName,
            Long roleId, UserType type, String urlPic, List<Long> operations) {
        super(username, email, displayName, roleId, type, urlPic, operations);
        this.id = id;
        this.enabled = enabled;
        this.created = created;
        this.updated = updated;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getUpdated() {
        return updated;
    }

    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }
}
