package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.MarketingMessage;
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
public class MarketingMessageDaoController extends EntityDao<MarketingMessage, Long> 
        implements MarketingMessageDao{

    private static final MarketingMessageDaoController INSTANCE 
            = new MarketingMessageDaoController();
    
    private MarketingMessageDaoController() {
        super(MarketingMessage.class);
    }

    public static MarketingMessageDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<MarketingMessage> findAll(int start, int size, Long customerId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<MarketingMessage> query = cb.createQuery(MarketingMessage.class);
            Root<MarketingMessage> mm = query.from(MarketingMessage.class);
            query.select(mm);
            
            Predicate restrictions = getRestrictions(manager, mm, customerId);
            if (restrictions != null)
                query.where(restrictions);
            
            TypedQuery<MarketingMessage> typedQuery = manager.createQuery(query);
            
            return size == 0 ? typedQuery.getResultList() : typedQuery
                    .setFirstResult(start).setMaxResults(size).getResultList();
        } catch(Exception ex) {
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
            Root<MarketingMessage> mm = query.from(MarketingMessage.class);
            query.select(cb.count(mm));
            
            Predicate restrictions = getRestrictions(manager, mm, customerId);
            if (restrictions != null)
                query.where(restrictions);
            
            return manager.createQuery(query).getSingleResult();
        } catch(Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    private Predicate getRestrictions(EntityManager manager, 
            Root<MarketingMessage> mm, Long customerId) {
        if (customerId != null) {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            Path<Long> customer = mm.get("customer").get("id");
            
            return cb.equal(customer, customerId);
        }
        
        return null;
    }

}
