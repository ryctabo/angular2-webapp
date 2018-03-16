package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class MarketingObsResponse extends MarketingObsData {

    private Long id;

    private UserResponse user;

    private CustomerDataDto customer;

    private Calendar registerDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Calendar registerDate) {
        this.registerDate = registerDate;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public CustomerDataDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDataDto customer) {
        this.customer = customer;
    }
}
