package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Base;
import com.nativapps.arpia.database.exception.DatabaseException;
import java.util.ArrayList;
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
public class BaseDaoController extends EntityDao<Base, Long> implements BaseDao {

    private static final BaseDaoController INSTANCE = new BaseDaoController();

    private BaseDaoController() {
        super(Base.class);
    }

    public static BaseDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Base> findAll(int start, int size, Long messengerId,
            Boolean debt) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Base> query = cb.createQuery(Base.class);

            Root<Base> base = query.from(Base.class);
            query.select(base);

            Predicate[] restrictions = getFilterRestrictions(manager, base,
                    messengerId, debt);
            if (restrictions != null)
                query.where(restrictions);
            query.orderBy(cb.desc(base.get("registerDate")));

            TypedQuery<Base> typedQuery = manager.createQuery(query);

            return size == 0 ? typedQuery.getResultList() : typedQuery
                    .setMaxResults(size).setFirstResult(start).getResultList();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    private Predicate[] getFilterRestrictions(EntityManager manager,
            Root<Base> base, Long messengerId, Boolean debt) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();

        List<Predicate> restric = new ArrayList<>();
        if (messengerId != null) {
            Path<Long> messenger = base.get("messenger").get("id");
            restric.add(cb.equal(messenger, messengerId));
        }
        if (debt != null) {
            Path<Integer> baseDelivery = base.get("baseDelivery");
            Path<Integer> baseReturn = base.get("baseReturn");
            restric.add(debt ? cb.gt(baseDelivery, baseReturn)
                    : cb.equal(baseDelivery, baseReturn));
        }
        return !restric.isEmpty() ? restric.toArray(new Predicate[restric
                .size()]) : null;
    }

    @Override
    public Base findLastRegister(Long messengerId) {
        EntityManager manager = getEntityManager();
        try {
            TypedQuery<Base> query = manager
                    .createNamedQuery("base.findLastRegister", Base.class)
                    .setParameter("messengerId", messengerId);
            List<Base> result = query.setMaxResults(1).getResultList();

            return result.isEmpty() ? null : result.get(0);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public Long findAllCount(Long messengerId, Boolean debt) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);

            Root<Base> base = query.from(Base.class);
            query.select(cb.count(base));

            Predicate[] restrictions = getFilterRestrictions(manager, base,
                    messengerId, debt);
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
}
