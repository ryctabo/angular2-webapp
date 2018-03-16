package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.DismissalHistory;
import java.util.List;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class DismissalHistoryDaoController extends EntityDao<DismissalHistory, Long>
        implements DismissalHistoryDao {

    private static final DismissalHistoryDaoController INSTANCE
            = new DismissalHistoryDaoController();

    private DismissalHistoryDaoController() {
        super(DismissalHistory.class);
    }

    public static DismissalHistoryDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<DismissalHistory> findAll(long messengerId) {
        return executeNamedQueryForList("dismissalHistory.findByMessengerId",
                new Parameter("id", messengerId));
    }

}
