package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.DomicileReview;
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
public class DomicileReviewDaoController extends EntityDao<DomicileReview, Long> 
        implements DomicileReviewDao{

    private static final DomicileReviewDaoController INSTANCE 
            = new DomicileReviewDaoController();
    
    private DomicileReviewDaoController() {
        super(DomicileReview.class);
    }

    public static DomicileReviewDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<DomicileReview> findAll(int start, int size, Long dexecId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<DomicileReview> query = cb.createQuery(DomicileReview.class);
            Root<DomicileReview> domicileReview = query.from(DomicileReview.class);
            query.select(domicileReview);
            
            if (dexecId != null)
                query.where(cb.equal(domicileReview.get("domicileExecuteId"), dexecId));
            
            TypedQuery<DomicileReview> typedQuery = manager.createQuery(query);
            
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
    public long findAllCount(Long dexecId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<DomicileReview> domicileReview = query.from(DomicileReview.class);
            query.select(cb.count(domicileReview));
            
            if (dexecId != null)
                query.where(cb.equal(domicileReview.get("domicileExecuteId"), dexecId));
            
            return manager.createQuery(query).getSingleResult();
        } catch(Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }
    

}
