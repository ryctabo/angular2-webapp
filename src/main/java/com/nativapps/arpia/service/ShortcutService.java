package com.nativapps.arpia.service;

import com.nativapps.arpia.model.dto.ShortcutRequest;
import com.nativapps.arpia.model.dto.ShortcutResponse;

import java.util.List;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public interface ShortcutService extends Service {

    /**
     * Get all shortcuts.
     *
     * @return shortcuts list
     */
    List<ShortcutResponse> get();

    /**
     * Get shortcut from the given ID.
     *
     * @param id shortcut ID
     * @return shortcut data
     */
    ShortcutResponse get(Long id);

    /**
     * Get shortcut from the given key parameter.
     *
     * @param key shortcut key
     * @return shortcut data
     */
    ShortcutResponse getByKey(String key);

    /**
     * Create new shortcut from the given data.
     *
     * @param data shortcut data
     * @return shortcut saved
     */
    ShortcutResponse add(ShortcutRequest data);

    /**
     * Update information of shortcut from the given data.
     *
     * @param id   shortcut ID
     * @param data shortcut data
     * @return shortcut updated
     */
    ShortcutResponse update(Long id, ShortcutRequest data);

    /**
     * Delete shortcut from the given ID.
     *
     * @param id shortcut ID
     * @return shortcut deleted
     */
    ShortcutResponse delete(Long id);

}
