package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.DismissalHistoryRequest;
import com.nativapps.arpia.model.dto.DismissalHistoryResponse;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface DismissalHistoryService extends Service {

    /**
     * Get all dismissal history.
     *
     * @return list of the dismissal history.
     */
    List<DismissalHistoryResponse> getAll();

    /**
     * Get all dismissal history from the given messenger ID.
     *
     * @param messengerId the messenger ID
     * @return list of the dismissal history
     */
    List<DismissalHistoryResponse> getAll(Long messengerId);

    /**
     * Get a dismissal history from the given ID.
     *
     * @param id the dismissal history ID
     * @return the dismissal history information
     */
    DismissalHistoryResponse get(Long id);

    /**
     * Create a new dismissal history.
     *
     * @param dismissalHistory dismissal history data
     * @return dismissal history saved
     */
    DismissalHistoryResponse add(DismissalHistoryRequest dismissalHistory);

}
