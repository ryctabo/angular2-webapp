package com.nativapps.arpia.service;

import com.nativapps.arpia.model.SystemConfig;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface SystemConfigService extends Service {

    /**
     * Returns the system configuration information
     * 
     * @return System configuration
     */
    SystemConfig get();
    
    /**
     * Updates the system configuration information
     * 
     * @param data System configuration information to update
     * @return Updated system configuration
     */
    SystemConfig update(SystemConfig data);
}
