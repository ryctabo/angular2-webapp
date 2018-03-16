package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.DataAccessObject;
import com.nativapps.arpia.database.entity.Shortcut;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public interface ShortcutDao extends DataAccessObject<Shortcut, Long> {

    /**
     * Find a shortcut by its key.
     *
     * @param key Shortcut key to search
     * @return Searched shortcut
     */
    Shortcut findByKey(String key);
}
