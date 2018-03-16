package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.MSettlement;
import com.nativapps.arpia.database.exception.DatabaseException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class MSettlementDaoController extends EntityDao<MSettlement, Long>
        implements MSettlementDao {

    private static final MSettlementDaoController INSTANCE = new MSettlementDaoController();

    private MSettlementDaoController() {
        super(MSettlement.class);
    }

    public static MSettlementDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<MSettlement> findAll(int start, int size, Long messengerId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<MSettlement> query = cb.createQuery(MSettlement.class);
            Root<MSettlement> ms = query.from(MSettlement.class);
            query.select(ms);

            Predicate restrictions = getRestrictions(manager, ms, messengerId);
            if (restrictions != null)
                query.where(restrictions);

            TypedQuery<MSettlement> typedQuery = manager.createQuery(query);

            return typedQuery.setFirstResult(start).setMaxResults(size)
                    .getResultList();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex); 
        }
    }

    private Predicate getRestrictions(EntityManager manager, Root<MSettlement> ms, Long messengerId) {
        if (messengerId != null) {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            Path<Long> messenger = ms.get("check").get("assignment")
                    .get("id").get("messenger").get("id");
            return cb.equal(messenger, messengerId);
        }
        return null;
    }

    @Override
    public long count(Long messengerId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<MSettlement> ms = query.from(MSettlement.class);
            query.select(cb.count(ms));

            Predicate restrictions = getRestrictions(manager, ms, messengerId);
            if (restrictions != null)
                query.where(restrictions);

            return manager.createQuery(query).getSingleResult();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        }
    }
}
