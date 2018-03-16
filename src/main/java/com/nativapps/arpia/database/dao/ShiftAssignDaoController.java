package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Shift;
import com.nativapps.arpia.database.entity.ShiftAssignment;
import com.nativapps.arpia.database.entity.ShiftCheck;
import com.nativapps.arpia.database.exception.DatabaseException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftAssignDaoController extends EntityDao<ShiftAssignment, ShiftAssignment.ShiftAssignmentPK> implements ShiftAssignmentDao {

    private static final ShiftAssignDaoController INSTANCE
            = new ShiftAssignDaoController();

    private ShiftAssignDaoController() {
        super(ShiftAssignment.class);
    }

    public static ShiftAssignDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ShiftAssignment> findAll(Shift.ShiftPK id, Boolean clockin,
            Boolean clockout) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<ShiftAssignment> query = cb.createQuery(ShiftAssignment.class);
            Root<ShiftAssignment> assign = query.from(ShiftAssignment.class);
            query.select(assign);

            Predicate[] restrictions = getRestrictions(manager, query, assign, id,
                    clockin, clockout);
            if (restrictions != null)
                query.where(restrictions);

            return manager.createQuery(query).getResultList();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    private Predicate[] getRestrictions(EntityManager manager,
            CriteriaQuery<ShiftAssignment> query, Root<ShiftAssignment> assign,
            Shift.ShiftPK id, Boolean clockin, Boolean clockout) {

        if (id != null) {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            List<Predicate> rest = new ArrayList<>();
            Path<Integer> shiftIndex = assign.get("id").get("shift").get("id")
                    .get("index");
            Path<Long> shiftplanningId = assign.get("id").get("shift").get("id")
                    .get("shiftplanningId");

            rest.add(cb.equal(shiftIndex, id.getIndex()));
            rest.add(cb.equal(shiftplanningId, id.getShiftplanningId()));

            if (clockin != null && !clockin) {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<ShiftCheck> check = subquery.from(ShiftCheck.class);
                subquery.select(cb.count(check));
                subquery.where(cb.equal(check.get("assignment").get("id"), assign.get("id")));
                rest.add(cb.equal(subquery, 0l));
            } else if (clockin != null || clockout != null) {
                Root<ShiftCheck> check = query.from(ShiftCheck.class);
                rest.add(cb.equal(check.get("assignment").get("id"),
                        assign.get("id")));

                if (clockin != null && clockin)
                    rest.add(cb.isNotNull(check.get("clockIn")));
                if (clockout != null) {
                    if (clockout) rest.add(cb.isNotNull(check.get("clockOut")));
                    else rest.add(cb.isNull(check.get("clockOut")));
                }
            }

            return rest.toArray(new Predicate[rest.size()]);
        }

        return null;
    }

    @Override
    public long count(Shift.ShiftPK id) {
        if (id != null)
            return executeCountNamedQuery("shift_assign.countByShiftId",
                    new Parameter("index", id.getIndex()),
                    new Parameter("shiftPId", id.getShiftplanningId()));
        return 0;
    }

}
