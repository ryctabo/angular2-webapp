package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.DailyOperation;
import com.nativapps.arpia.database.exception.DatabaseException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class DailyOperationDaoController extends EntityDao<DailyOperation, Long>
        implements DailyOperationDao {

    private static final DailyOperationDaoController INSTANCE
            = new DailyOperationDaoController();

    private DailyOperationDaoController() {
        super(DailyOperation.class);
    }

    public static DailyOperationDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<DailyOperation> findAll(int start, int size, Long operationId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<DailyOperation> query = cb.createQuery(DailyOperation.class);
            Root<DailyOperation> dailyOp = query.from(DailyOperation.class);
            query.select(dailyOp);

            Predicate restrictions = getRestrictions(manager, dailyOp, operationId);
            if (restrictions != null)
                query.where(restrictions);
            
            TypedQuery<DailyOperation> typedQuery = manager.createQuery(query);

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
            Root<DailyOperation> dailyOp = query.from(DailyOperation.class);
            query.select(cb.count(dailyOp));

            Predicate restrictions = getRestrictions(manager, dailyOp, operationId);
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

    @Override
    public DailyOperation last(Long operationId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<DailyOperation> query = cb.createQuery(DailyOperation.class);
            Root<DailyOperation> dailyOp = query.from(DailyOperation.class);
            query.select(dailyOp);

            Predicate restrictions = getRestrictions(manager, dailyOp, operationId);
            if (restrictions != null)
                query.where(restrictions);
            
            query.orderBy(cb.desc(dailyOp.get("id")));
            
            List<DailyOperation> resultList = manager.createQuery(query)
                    .setMaxResults(1).getResultList();
            
            return resultList.isEmpty() ? null : resultList.get(0);
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    private Predicate getRestrictions(EntityManager manager, 
            Root<DailyOperation> dailyOp, Long operationId) {
        
        if (operationId != null) {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            Path<Long> operation = dailyOp.get("operation").get("id");
            
            return cb.equal(operation, operationId);
        }
        
        return null;
    }

    @Override
    public DailyOperation find(Long operationId, Long id) {
        return executeNamedQuery("dailyOp.findByOperationId", 
                new Parameter("operationId", operationId), new Parameter("id", id));
    }
}
