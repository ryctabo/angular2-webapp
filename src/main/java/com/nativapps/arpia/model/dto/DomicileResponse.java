package com.nativapps.arpia.model.dto;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public class DomicileResponse extends DomicileData {

    private Long id;

    private List<LocationResponse> locations;

    private UserResponse user;

    private Calendar createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<LocationResponse> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationResponse> locations) {
        this.locations = locations;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

}
