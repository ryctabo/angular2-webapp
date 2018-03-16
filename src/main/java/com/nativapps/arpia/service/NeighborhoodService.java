package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.*;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.2.0
 */
public interface NeighborhoodService extends Service {

    /**
     * Returns neighborhood list paginated
     *
     * @param start  Initial index
     * @param size   List size
     * @param search String to filter in neighborhood resource
     * @return Neighborhood list
     */
    ListResponse<NeighborhoodResponse> getAll(int start, int size, String search);

    /**
     * Returns a specific neighborhood by ID
     *
     * @param id Neighborhood ID
     * @return Searched entity
     */
    NeighborhoodResponse get(Long id);

    /**
     * Create a new neighborhood
     *
     * @param data Neighborhood information
     * @return Neighborhood created
     */
    NeighborhoodResponse add(NeighborhoodRequest data);

    /**
     * Update a neighborhood
     *
     * @param id   Neighborhood ID
     * @param data Neighborhood information
     * @return Updated neighborhood
     */
    NeighborhoodResponse update(Long id, NeighborhoodRequest data);

    /**
     * Delete a neigborhood
     *
     * @param id Neighborhood ID
     * @return Deleted neighborhood
     */
    NeighborhoodResponse delete(Long id);

    /**
     * Get all domiciles executes from the given neighborhood ID parameter.
     *
     * @param neighborhood the neighborhood ID
     * @param start        index of first element to get
     * @param size         list size
     * @return list of domicile executes
     */
    ListResponse<DomicileExecuteResponse> getDomiciles(long neighborhood, int start, int size);

    /**
     * Get all customer from the given neighborhood ID parameter.
     *
     * @param neighborhood the neighborhood ID
     * @param start        index of first element to get
     * @param size         list size
     * @return list of customer data
     */
    ListResponse<CustomerDataDto> getCustomers(long neighborhood, int start, int size);
}
