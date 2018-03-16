package com.nativapps.arpia.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class SystemConfigManagerTest {

    private static final Logger LOG = Logger.getLogger(SystemConfigManagerTest.class.getName());
    
    @Test
    public void testGet() {
        SystemConfig config = SystemConfigManager.get();
        LOG.log(Level.INFO, "{0}", config);
        assertNotNull(config);
    }
    
//    @Test
    public void testSet() {
        SystemConfig config = new SystemConfig();
        config.setAmount(60000);
        SystemConfigManager.set(config);
    }
}
