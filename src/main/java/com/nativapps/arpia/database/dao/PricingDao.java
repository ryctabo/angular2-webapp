package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Pricing;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public interface PricingDao extends DataAccessObject<Pricing, Pricing.PricingPK> {

    /**
     * Delete all pricing elements from the given map point ID.
     *
     * @param mapPointId map point ID
     */
    void deleteByMapPointId(long mapPointId);

}
