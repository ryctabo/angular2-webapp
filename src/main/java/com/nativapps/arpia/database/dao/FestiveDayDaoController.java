package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.FestiveDay;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class FestiveDayDaoController extends EntityDao<FestiveDay, Long>
        implements FestiveDayDao {

    private static final FestiveDayDaoController INSTANCE
            = new FestiveDayDaoController();

    private FestiveDayDaoController() {
        super(FestiveDay.class);
    }

    public static FestiveDayDaoController getInstance() {
        return INSTANCE;
    }

}
