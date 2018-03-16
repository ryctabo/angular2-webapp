package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class MarketingMessageResponse extends MarketingMessageData {

    private Long id;
    
    private Calendar registerDate;
    
    private CustomerDataDto customer;
    
    private UserResponse user;

    public MarketingMessageResponse() {
    }

    public MarketingMessageResponse(Long id, Calendar registerDate, 
            String observations, Boolean visit, Boolean call) {
        super(observations, visit, call);
        this.id = id;
        this.registerDate = registerDate;
    }

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

    public CustomerDataDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDataDto customer) {
        this.customer = customer;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
