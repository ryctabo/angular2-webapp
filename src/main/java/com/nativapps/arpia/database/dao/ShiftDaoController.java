package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Shift;
import com.nativapps.arpia.database.exception.DatabaseException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftDaoController extends EntityDao<Shift, Shift.ShiftPK> 
        implements ShiftDao {

    private static final ShiftDaoController INSTANCE = new ShiftDaoController();
    
    private ShiftDaoController() {
        super(Shift.class);
    }

    public static ShiftDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Shift> findAll(Long shiftplanningId) {
        return executeNamedQueryForList("shift.findAllByShiftplanningId", 
                new Parameter("shiftPId", shiftplanningId));
    }

    @Override
    public int count(Long shiftplanningId) {
        EntityManager manager = getEntityManager();
        try {
            Query query = manager.createNamedQuery("shift.count")
                    .setParameter("shiftPId", shiftplanningId);
            return ((Long) query.getSingleResult()).intValue();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }
}
