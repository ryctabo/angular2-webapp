package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Abbreviation;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public class AbbreviationDaoController extends EntityDao<Abbreviation, Long> implements AbbreviationDao {

    private final static AbbreviationDaoController INSTANCE = new AbbreviationDaoController();

    private AbbreviationDaoController() {
        super(Abbreviation.class);
    }

    public static AbbreviationDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public Abbreviation findByShortText(String shortText) {
        return executeNamedQuery("abbreviation.findByShortText",
                new Parameter("shortText", shortText));
    }

}
