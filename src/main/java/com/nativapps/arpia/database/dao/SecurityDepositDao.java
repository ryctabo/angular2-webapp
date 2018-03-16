package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.SecurityDeposit;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.model.OrderType;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public interface SecurityDepositDao
        extends DataAccessObject<SecurityDeposit, Long> {

    /**
     * Get paid to date by security deposit user id provided.
     *
     * @param ownerId user owner id to search.
     * @return total paid ti date.
     */
    double paidToDate(Long ownerId);

    /**
     * Return a filter user security deposit list.
     *
     * @param search string to search
     * @param start initial index
     * @param size list size
     * @param orderBy property to ordering
     * @param orderType ASC or DESC
     *
     * @return messenger list
     */
    List<User> findAll(String search, int start, int size,
            String orderBy, OrderType orderType);

    /**
     * Return a filter {@link SecurityDeposit} list.
     *
     * @param search string to search
     * @param start initial index
     * @param size list size
     * @param from
     * @param to
     * @param orderBy property to ordering
     * @param orderType ASC or DESC
     *
     * @return messenger list
     */
    List<SecurityDeposit> findAllPayments(String search, int start, int size,
            Date from, Date to,
            String orderBy, OrderType orderType);


    /**
     * Get count of all register inner {@link SecurityDeposit} table.
     *
     * @return total count of security deposit
     */
    long getCountPayments();
}
