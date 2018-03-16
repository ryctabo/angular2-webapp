package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.entity.MessengerPlanning;
import com.nativapps.arpia.database.entity.Shift;
import com.nativapps.arpia.database.entity.ShiftAssignment;
import com.nativapps.arpia.database.entity.Shiftplanning;
import com.nativapps.arpia.database.exception.DatabaseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftplanningDaoController extends EntityDao<Shiftplanning, Long> 
        implements ShiftplanningDao {

    private static final ShiftplanningDaoController INSTANCE 
            = new ShiftplanningDaoController();
    
    private ShiftplanningDaoController() {
        super(Shiftplanning.class);
    }

    public static ShiftplanningDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Shiftplanning> findAll(int start, int size, Long operationId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Shiftplanning> query = cb.createQuery(Shiftplanning.class);

            Root<Shiftplanning> sp = query.from(Shiftplanning.class);
            query.select(sp);

            Predicate restrictions = getRestrictions(manager, sp, operationId);
            if (restrictions != null)
                query.where(restrictions);

            TypedQuery<Shiftplanning> typedQuery = manager.createQuery(query);

            return size == 0 ? typedQuery.getResultList() : typedQuery
                    .setFirstResult(start).setMaxResults(size).getResultList();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public long findAllCount(Long operationId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);

            Root<Shiftplanning> sp = query.from(Shiftplanning.class);
            query.select(cb.count(sp));

            Predicate restrictions = getRestrictions(manager, sp, operationId);
            if (restrictions != null)
                query.where(restrictions);

            return manager.createQuery(query).getSingleResult();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    private Predicate getRestrictions(EntityManager manager, 
            Root<Shiftplanning> sp, Long operationId) {
        if (operationId != null) {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            Path<Long> operation = sp.get("operation").get("id");
            return cb.equal(operation, operationId);
        }
        return null;
    }

    @Override
    public Shiftplanning findByDate(Calendar date) {
        return executeNamedQuery("shiftplanning.findByDate", 
                new Parameter("date", date));
    }

    @Override
    public List<MessengerPlanning> getMessengerPlanning(int start, int size, 
            Long shiftplanningId, Boolean assign) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery query = cb.createQuery();
            Root<Messenger> m = query.from(Messenger.class);
            Join<Messenger, ShiftAssignment> a = m.join("assignments", JoinType.LEFT);
            
            Path<Long> sid = a.get("id").get("shift").get("id").get("shiftplanningId");
            a.on(cb.equal(sid, shiftplanningId));
            query.multiselect(m, a.get("id").get("shift"));
            
            if (assign != null) {
                if (assign) query.where(cb.isNotNull(sid));
                else query.where(cb.isNull(sid));
            }
            
            TypedQuery typedQuery = manager.createQuery(query);
            List<Object[]> resultList = size == 0 ? typedQuery.getResultList() : typedQuery
                    .setFirstResult(start).setMaxResults(size).getResultList();
            
            List<MessengerPlanning> result = new ArrayList<>();
            for (Object[] item : resultList) {
                result.add(new MessengerPlanning((Messenger) item[0], (Shift) item[1]));
            }
            
            return result;
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public long getMessengerPlanningCount(Long shiftplanningId, Boolean assign) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<Messenger> m = query.from(Messenger.class);
            Join<Messenger, ShiftAssignment> a = m.join("assignments", JoinType.LEFT);
            
            Path<Long> sid = a.get("id").get("shift").get("id").get("shiftplanningId");
            a.on(cb.equal(sid, shiftplanningId));
            query.select(cb.count(a));
            
            if (assign != null) {
                if (assign) query.where(cb.isNotNull(sid));
                else query.where(cb.isNull(sid));
            }
            
            return manager.createQuery(query).getSingleResult();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

}
