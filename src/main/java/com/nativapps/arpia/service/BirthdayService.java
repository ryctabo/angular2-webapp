package com.nativapps.arpia.service;

import com.nativapps.arpia.model.adapter.BirthdayPeriod;
import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.ParticularResponse;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1-SNAPSHOT
 */
public interface BirthdayService extends Service {

    /**
     * Get all particular customers from the given parameters.
     *
     * @param start  index of first element to get
     * @param size   list size
     * @param period define the filtering period
     * @return particular customers list
     */
    ListResponse<ParticularResponse> getAll(int start, int size, BirthdayPeriod period);
}
