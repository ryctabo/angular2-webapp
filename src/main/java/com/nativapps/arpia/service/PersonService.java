package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.PersonResponse;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface PersonService extends Service {

    /**
     * Returns a list with the people information registers
     * 
     * @param start Initial index
     * @param size List size
     * @param search String to filter in the people search
     * @return People list
     */
    ListResponse<PersonResponse> getAll(int start, int size, String search);
    
    /**
     * Returns a specific person by provided ID
     * 
     * @param id Person ID
     * @return Searched person
     */
    PersonResponse get(Long id);
}
