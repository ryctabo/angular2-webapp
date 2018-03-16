package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Debt;
import com.nativapps.arpia.database.exception.DatabaseException;
import com.nativapps.arpia.model.OrderType;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class DebtDaoController extends EntityDao<Debt, Long> implements DebtDao {

    public static final Logger LOG = Logger
            .getLogger(DebtDaoController.class.getName());

    public static final DebtDaoController INSTANCE = new DebtDaoController();

    public DebtDaoController() {
        super(Debt.class);
    }

    public static DebtDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Debt> findAll(String search, Calendar from, Calendar to,
            int start, int size, String orderBy, OrderType type,
            Boolean closureState) {

        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Debt> cq = cb.createQuery(Debt.class);
                Root<Debt> d = cq.from(Debt.class);

                Predicate[] predicates = getPredicates(search, cb, d, from, to,
                        closureState);
                if (predicates != null)
                    cq.where(predicates);

                Path propOrderBy = d.get("id");
                if (orderBy != null)
                    propOrderBy = d.get(orderBy);

                Order order;
                if (type == null || type == OrderType.DESC)
                    order = cb.desc(propOrderBy);
                else
                    order = cb.asc(propOrderBy);
                cq.orderBy(order);

                TypedQuery<Debt> query = entityManager.createQuery(cq);
                return query.setFirstResult(start)
                        .setMaxResults(size)
                        .getResultList();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
                throw new DatabaseException(ex.getCause().getMessage(), ex);
            } finally {
                entityManager.close();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public long getCount(String search, Calendar from, Calendar to,
            Boolean closureState) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> cq = cb.createQuery(Long.class);
                Root<Debt> d = cq.from(Debt.class);

                cq.select(cb.count(d));

                Predicate[] predicates = getPredicates(search, cb, d, from, to,
                        closureState);
                if (predicates != null)
                    cq.where(predicates);

                return entityManager.createQuery(cq).getSingleResult();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
                throw new DatabaseException(ex.getCause().getMessage(), ex);
            } finally {
                entityManager.close();
            }
        }
        return -1L;
    }

    /**
     * Get restrictions from the given data.
     *
     * @param search search param
     * @param cb criteria builder
     * @param e debt structure
     * @param from start date
     * @param to end date
     * @param closureState the debts closure state
     * @return restrictions array
     */
    private Predicate[] getPredicates(String search, CriteriaBuilder cb,
            Root<Debt> d, Calendar from, Calendar to,
            Boolean closureState) {

        Set<Predicate> restrictions = new HashSet<>();
        if (search != null) {
            Path<String> concept = d.get("concept");
            restrictions.add(cb.like(concept, "%" + search + "%"));
        }

        Path<Calendar> sd = d.get("created");

        if (from != null)
            restrictions.add(cb.greaterThanOrEqualTo(sd, from));

        if (to != null)
            restrictions.add(cb.lessThanOrEqualTo(sd, to));

        if (closureState != null) {
            Path closure = d.get("closure");
            if (closureState) {
                restrictions.add(cb.isNotNull(closure));
            } else {
                restrictions.add(cb.isNull(closure));
            }
        }

        return restrictions.isEmpty() ? null : restrictions.toArray(new Predicate[restrictions.size()]);
    }

}
