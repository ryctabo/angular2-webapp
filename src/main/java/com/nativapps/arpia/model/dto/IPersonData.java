package com.nativapps.arpia.model.dto;

import com.nativapps.arpia.database.entity.Gender;
import java.util.Calendar;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface IPersonData {

    String getIdentification();

    String getName();

    String getLastName();

    Gender getGender();

    Calendar getBirthday();
}
