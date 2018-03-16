package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Shortcut;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @version 1.0
 */
public class ShortcutDaoController extends EntityDao<Shortcut, Long> implements ShortcutDao {

    private static final ShortcutDaoController INSTANCE = new ShortcutDaoController();

    private ShortcutDaoController() {
        super(Shortcut.class);
    }

    public static ShortcutDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public Shortcut findByKey(String key) {
        return executeNamedQuery("shortcut.findByKey", new Parameter("key", key));
    }

}
