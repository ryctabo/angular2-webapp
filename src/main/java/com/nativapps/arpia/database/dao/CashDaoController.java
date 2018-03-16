package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Cash;
import com.nativapps.arpia.database.exception.DatabaseException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class CashDaoController extends EntityDao<Cash, Long> implements CashDao {

    private static final CashDaoController INSTANCE = new CashDaoController();

    private CashDaoController() {
        super(Cash.class);
    }

    public static CashDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Cash> findAll(int start, int size) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Cash> query = cb.createQuery(Cash.class);
            Root<Cash> cash = query.from(Cash.class);
            query.select(cash);
            query.orderBy(cb.desc(cash.get("registerDate")));
            
            TypedQuery<Cash> typedQuery = manager.createQuery(query);
            
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
    public long findAllCount() {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<Cash> cash = query.from(Cash.class);
            query.select(cb.count(cash));
            
            return manager.createQuery(query).getSingleResult();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public Cash lastRegister() {
        EntityManager manager = getEntityManager();
        try {
            List<Cash> resultList = manager
                    .createNamedQuery("cash.lastRegister", Cash.class)
                    .setMaxResults(1).getResultList();
            
            return resultList.isEmpty() ? null : resultList.get(0);
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }
}
