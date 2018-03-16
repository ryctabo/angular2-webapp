package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ShiftCheckKeyRequest;
import com.nativapps.arpia.model.dto.ShiftCheckKeyResponse;

import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public interface ShiftCheckKeyService extends Service {

    /**
     * Returns in a paginated way all the keys filtered by shift check ID
     *
     * @param shiftplanningId planning ID to filter
     * @param shiftIndex shift index
     * @param messengerId messenger ID who belongs the keys
     * @return key list
     */
    List<ShiftCheckKeyResponse> getAll(Long shiftplanningId, Integer shiftIndex,
                                       Long messengerId);

    /**
     * Generate a new key to a messenger
     *
     * @param shiftplanningId planning ID
     * @param shiftIndex shift index
     * @param messengerId messenger ID to assign the key
     * @return generated key
     */
    ShiftCheckKeyResponse generateKey(Long shiftplanningId, Integer shiftIndex,
                                      Long messengerId);

    /**
     * Allows to use a check key from a messenger
     *
     * @param shiftplanningId planning ID
     * @param shiftIndex shift index
     * @param messengerId messenger ID who belongs the key
     * @param request key information
     * @return used key
     */
    ShiftCheckKeyResponse useKey(Long shiftplanningId, Integer shiftIndex,
                                 Long messengerId, ShiftCheckKeyRequest request);
}
