package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.GoodStanding;
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
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class GoodStandingDaoController extends EntityDao<GoodStanding, Long> 
        implements GoodStandingDao{

    private static final GoodStandingDaoController INSTANCE 
            = new GoodStandingDaoController();
    
    private GoodStandingDaoController() {
        super(GoodStanding.class);
    }

    public static GoodStandingDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<GoodStanding> findAll(int start, int size, Long messengerId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<GoodStanding> query = cb.createQuery(GoodStanding.class);

            Root<GoodStanding> gs = query.from(GoodStanding.class);
            query.select(gs);

            Predicate restrictions = getRestrictions(manager, gs, messengerId);
            if (restrictions != null)
                query.where(restrictions);

            TypedQuery<GoodStanding> typedQuery = manager.createQuery(query);

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
    public long findAllCount(Long messengerId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);

            Root<GoodStanding> gs = query.from(GoodStanding.class);
            query.select(cb.count(gs));

            Predicate restrictions = getRestrictions(manager, gs, messengerId);
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
            Root<GoodStanding> gs, Long messengerId) {
        if (messengerId != null) {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            Path<Long> messenger = gs.get("messenger").get("id");
            return cb.equal(messenger, messengerId);
        }
        return null;
    }

}
