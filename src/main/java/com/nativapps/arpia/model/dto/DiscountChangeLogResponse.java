/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class DiscountChangeLogResponse {

    private Integer index;

    private UserResponse user;

    private Calendar date;

    public DiscountChangeLogResponse() {
    }

    public DiscountChangeLogResponse(UserResponse user, Calendar date, Integer index) {
        this.index = index;
        this.user = user;
        this.date = date;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}
