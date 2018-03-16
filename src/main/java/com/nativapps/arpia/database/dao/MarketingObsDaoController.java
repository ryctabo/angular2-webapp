package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.MarketingObs;
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
public class MarketingObsDaoController extends EntityDao<MarketingObs, Long>
        implements MarketingObsDao {

    private static final MarketingObsDaoController INSTANCE 
            = new MarketingObsDaoController();
    
    private MarketingObsDaoController() {
        super(MarketingObs.class);
    }

    public static MarketingObsDaoController getInstance() {
        return INSTANCE;
    }
    
    @Override
    public List<MarketingObs> findAll(int start, int size, Long customerId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<MarketingObs> query = cb.createQuery(MarketingObs.class);
            Root<MarketingObs> mo = query.from(MarketingObs.class);
            query.select(mo);
            
            Predicate restrictions = getRestrictions(manager, mo, customerId);
            if (restrictions != null)
                query.where(restrictions);
            
            TypedQuery<MarketingObs> typedQuery = manager.createQuery(query);
            
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
    public long findAllCount(Long customerId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<MarketingObs> mo = query.from(MarketingObs.class);
            query.select(cb.count(mo));
            
            Predicate restrictions = getRestrictions(manager, mo, customerId);
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
            Root<MarketingObs> mo, Long customerId) {
        if (customerId != null) {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            Path<Long> customer = mo.get("customer").get("id");
            
            return cb.equal(customer, customerId);
        }
        
        return null;
    }

}
