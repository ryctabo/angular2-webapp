package com.nativapps.arpia.service;

import com.nativapps.arpia.model.SystemConfig;
import com.nativapps.arpia.model.SystemConfigManager;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class SystemConfigServiceImpl extends GenericService
        implements SystemConfigService {

    @Override
    public SystemConfig get() {
        return SystemConfigManager.get();
    }

    @Override
    public SystemConfig update(SystemConfig data) {
        SystemConfig systemConfig = SystemConfigManager.get();

        if (data != null) {
            if (data.getAmount() != 0)
                systemConfig.setAmount(data.getAmount());
            if (data.getSecurityDeposit() != 0)
                systemConfig.setSecurityDeposit(data.getSecurityDeposit());
            if (data.getDenominations() != null)
                systemConfig.setDenominations(data.getDenominations());
            if (data.getQuestions() != null)
                systemConfig.setQuestions(data.getQuestions());
            SystemConfigManager.set(systemConfig);
        }

        return systemConfig;
    }
}
