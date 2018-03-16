package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ListResponse;
import com.nativapps.arpia.model.dto.MSettlementRequest;
import com.nativapps.arpia.model.dto.MSettlementResponse;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface MSettlementService extends Service {

    /**
     * Returns all the messenger settlement
     *
     * @param start initial index
     * @param size list size
     * @param messengerId messenger ID to filter
     * @return messenger settlement list
     */
    ListResponse<MSettlementResponse> getAll(int start, int size, Long messengerId);

    /**
     * Return a specific messenger settlement by ID
     *
     * @param id messenger settlement ID
     * @return messenger settlement
     */
    MSettlementResponse get(Long id);

    /**
     * Add a new messenger settlement
     *
     * @param request messenger settlement information
     * @return added messenger settlement
     */
    MSettlementResponse add(MSettlementRequest request);

    /**
     * Show the messenger settlement before its execution
     *
     * @param request messenger settlement information
     * @return messenger settlement
     */
    MSettlementResponse pre(MSettlementRequest request);
}
