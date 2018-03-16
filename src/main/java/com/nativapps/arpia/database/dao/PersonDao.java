package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Person;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public interface PersonDao extends DataAccessObject<Person, Long> {

    /**
     * Get information about a person if existe with the given identification.
     *
     * @param identification identification
     * @return person data
     */
    Person findByIdentification(String identification);
    
    /**
     * Returns all the people registers
     * 
     * @param start Initial index
     * @param size List size
     * @param search String to filter in the person search
     * @return Person List
     */
    List<Person> findAll(int start, int size, String search);
    
    /**
     * Returns the find all result count
     * 
     * @param search String to filter in the person search
     * @return Result count
     */
    long findAllCount(String search);
}
