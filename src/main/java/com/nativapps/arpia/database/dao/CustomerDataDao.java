package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.CustomerData;
import com.nativapps.arpia.database.entity.Establishment;
import com.nativapps.arpia.database.entity.Particular;

import java.util.Calendar;
import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public interface CustomerDataDao extends DataAccessObject<CustomerData, Long> {

    /**
     * Returns a customer list paginated
     *
     * @param start  Initial index
     * @param size   List size
     * @param search String to filter the customer search
     * @return Customer list
     */
    List<CustomerData> findAllPaginated(int start, int size, String search);

    /**
     * Returns a findAll's result count
     *
     * @param search String to filter the customer search
     * @return Result count
     */
    Long findAllCount(String search);

    /**
     * Search a particular information by customer ID
     *
     * @param customerId Customer ID
     * @return Particular Information
     */
    Particular findParticular(Long customerId);

    /**
     * Search a establishment information by customer ID
     *
     * @param customerId Customer ID
     * @return Establishment Information
     */
    Establishment findEstablishment(Long customerId);

    /**
     * find all the customer by not communication date
     *
     * @param start Initial index
     * @param size  List size
     * @param date  Not communication date
     * @return Customer list
     */
    List<CustomerData> findByNotCommunication(int start, int size, Calendar date);

    /**
     * Return the result count of findByNotCommunication
     *
     * @param date Not communication date
     * @return Result count
     */
    long countByNotCommunication(Calendar date);

    /**
     * Get all customers from the given neighborhood ID parameter.
     *
     * @param neighborhood the neighborhood ID
     * @param start        index of first element to get
     * @param size         list size
     * @return list of customers
     */
    List<CustomerData> findByNeighborhood(long neighborhood, int start, int size);

    /**
     * Get total count of customers from the given neighborhood ID parameter.
     *
     * @param neighborhood the neighborhood ID
     * @return total of elements
     */
    long countByNeighborhood(long neighborhood);

}
