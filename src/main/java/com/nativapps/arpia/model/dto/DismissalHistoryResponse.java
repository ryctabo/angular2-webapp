package com.nativapps.arpia.model.dto;

import java.util.Calendar;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DismissalHistoryResponse extends DismissalHistoryData {

    private Long id;

    private Calendar register;

    public DismissalHistoryResponse() {
    }

    public DismissalHistoryResponse(Long id, Calendar register,
            Boolean dismissal, String reason, Long messengerId) {
        super(dismissal, reason, messengerId);
        this.id = id;
        this.register = register;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getRegister() {
        return register;
    }

    public void setRegister(Calendar register) {
        this.register = register;
    }

}
