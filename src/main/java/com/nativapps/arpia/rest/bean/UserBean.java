package com.nativapps.arpia.rest.bean;

import com.nativapps.arpia.database.entity.UserType;

import javax.ws.rs.QueryParam;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class UserBean extends GenericFilterBean {

    @QueryParam("type")
    private UserType type;

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

}
