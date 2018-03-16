package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.entity.Particular;
import com.nativapps.arpia.model.adapter.BirthdayPeriod;

import java.util.List;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1-SNAPSHOT
 */
public interface BirthdayDao {

    /**
     * Get all particular customers from the given parameters.
     *
     * @param start  index of first element to get
     * @param size   list size
     * @param period define the filtering period
     * @return particular customers list
     */
    List<Particular> findAll(int start, int size, BirthdayPeriod period);

    /**
     * Get count all elements from the given parameter.
     *
     * @param period define the filtering period
     * @return {@code long} value
     */
    long count(BirthdayPeriod period);
}
