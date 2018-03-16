package com.nativapps.arpia.service;

import java.io.OutputStream;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public interface GoodStandingRService extends Service {
    
    /**
     * Fill the OutputStream parameter with the report information 
     * about the good standing
     * 
     * @param gsId good standing id
     * @param out outputStream to fill
     */
    void get(Long gsId, OutputStream out);
}
