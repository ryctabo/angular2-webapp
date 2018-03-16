package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.CashTallying;
import com.nativapps.arpia.database.exception.DatabaseException;
import java.util.ArrayList;
import java.util.Calendar;
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
public class CashTallyingDaoController extends EntityDao<CashTallying, Long> 
        implements CashTallyingDao {
    
    private static final CashTallyingDaoController INSTANCE 
            = new CashTallyingDaoController();

    private CashTallyingDaoController() {
        super(CashTallying.class);
    }

    public static CashTallyingDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<CashTallying> findAll(int start, int size, Calendar from, 
            Calendar to) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<CashTallying> query = cb.createQuery(CashTallying.class);
            Root<CashTallying> cashT = query.from(CashTallying.class);
            query.select(cashT);
            
            Predicate restrictions = getRestrictions(manager, cashT, 
                    from, to);
            if (restrictions != null)
                query.where(restrictions);
            
            TypedQuery<CashTallying> typedQuery = manager.createQuery(query);
            
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
    public long findAllCount(Calendar from, Calendar to) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<CashTallying> cashT = query.from(CashTallying.class);
            query.select(cb.count(cashT));
            
            Predicate restrictions = getRestrictions(manager, cashT, 
                    from, to);
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
            Root<CashTallying> cashT, Calendar from, Calendar to) {
        
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        Path<Calendar> registerDate = cashT.get("registerDate");
        List<Predicate> res = new ArrayList<>();
        if (from != null)
            res.add(cb.greaterThanOrEqualTo(registerDate, from));
        if (to != null)
            res.add(cb.lessThan(registerDate, to));
        
        return res.isEmpty() ? null : 
                cb.and(res.toArray(new Predicate[res.size()]));
    }
}
