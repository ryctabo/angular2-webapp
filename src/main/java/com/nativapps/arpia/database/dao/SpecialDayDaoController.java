package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.SpecialDay;

public class SpecialDayDaoController extends EntityDao<SpecialDay, Long>
        implements SpecialDayDao {

    private final static SpecialDayDaoController INSTANCE
            = new SpecialDayDaoController();

    private SpecialDayDaoController() {
        super(SpecialDay.class);
    }

    public static SpecialDayDaoController getInstance() {
        return INSTANCE;
    }

}
