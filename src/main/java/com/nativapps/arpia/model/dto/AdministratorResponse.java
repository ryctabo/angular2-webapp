package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Gender;
import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class AdministratorResponse extends AdministratorData {

    private Long id;

    public AdministratorResponse() {
    }

    public AdministratorResponse(Long id, String identification, String name,
            String lastName, Gender gender, Calendar birthday, String address,
            String email, String phone, Boolean linked, Integer nDayReport,
            String observations) {
        super(identification, name, lastName, gender, birthday, address, email,
                phone, linked, nDayReport, observations);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
