package com.nativapps.arpia.service;

import com.nativapps.arpia.database.entity.ChipType;
import com.nativapps.arpia.model.dto.ChipRequest;
import com.nativapps.arpia.model.dto.ChipResponse;
import com.nativapps.arpia.model.dto.ListResponse;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface ChipService extends Service {

    /**
     * Get all chips from the given parameters.
     *
     * @param messengerId the messenger ID
     * @param type        chip type
     * @param start       index of element to get
     * @param size        list size
     * @return list of chips
     */
    ListResponse<ChipResponse> get(Long messengerId, ChipType type, int start, int size);

    /**
     * Get chip from the given ID.
     *
     * @param id the chip ID
     * @return chip data
     */
    ChipResponse get(Long id);

    /**
     * Add a new chip.
     *
     * @param chip chip data
     * @return chip saved
     */
    ChipResponse add(ChipRequest chip);

}
