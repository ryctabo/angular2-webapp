package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.Shift;
import com.nativapps.arpia.database.entity.ShiftAssignment;
import com.nativapps.arpia.database.entity.ShiftCheck;
import com.nativapps.arpia.database.exception.DatabaseException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftCheckDaoController extends EntityDao<ShiftCheck, Long> 
        implements ShiftCheckDao {

    private final static ShiftCheckDaoController INSTANCE
            = new ShiftCheckDaoController();
    
    private ShiftCheckDaoController() {
        super(ShiftCheck.class);
    }

    public static ShiftCheckDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public ShiftCheck find(ShiftAssignment.ShiftAssignmentPK assignId) {
        if (assignId != null)
            return executeNamedQuery("shift_check.findByAssignId", 
                    new Parameter("messengerId", assignId.getMessenger().getId()),
                    new Parameter("shiftPId", assignId.getShift().getId()
                            .getShiftplanningId()),
                    new Parameter("index", assignId.getShift().getId().getIndex()));
        return null;
    }

    @Override
    public List<ShiftCheck> findAll(Shift.ShiftPK shiftId, Long messengerId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<ShiftCheck> query = cb.createQuery(ShiftCheck.class);
            Root<ShiftCheck> check = query.from(ShiftCheck.class);
            Root<Shift> shift = query.from(Shift.class);
            query.select(check);
            
            if (shiftId != null) {
                Path<Long> spid = shift.get("id").get("shiftplanningId");
                Path<Integer> sindex = shift.get("id").get("index");
                
                Predicate pSpid = cb.equal(spid, shiftId.getShiftplanningId());
                Predicate pIndex = cb.equal(sindex, shiftId.getIndex());
                
                if (messengerId != null) {
                    Path<Long> mid = check.get("assignment").get("id")
                            .get("messenger").get("id");
                    
                    Predicate pMId = cb.equal(mid, messengerId);
                    query.where(cb.and(pIndex, pSpid, pMId));
                } else
                    query.where(cb.and(pSpid, pIndex));
            }
            
            return manager.createQuery(query).getResultList();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }
}
