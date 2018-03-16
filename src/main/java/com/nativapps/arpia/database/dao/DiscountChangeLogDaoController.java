package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.DiscountChangeLog;
import com.nativapps.arpia.database.exception.DatabaseException;
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
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0
 */
public class DiscountChangeLogDaoController extends EntityDao<DiscountChangeLog, Long> implements DiscountChangeLogDao {

    public static final Logger LOG = Logger
            .getLogger(DiscountChangeLogDaoController.class.getName());

    public static final DiscountChangeLogDaoController INSTANCE = new DiscountChangeLogDaoController();

    private DiscountChangeLogDaoController() {
        super(DiscountChangeLog.class);
    }

    public static DiscountChangeLogDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<DiscountChangeLog> findAll(Long discountId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery cq = cb.createQuery(DiscountChangeLog.class);
                Root<DiscountChangeLog> log = cq.from(DiscountChangeLog.class);

                Predicate[] predicates = getPredicates(discountId, cb, log);

                if (predicates != null)
                    cq.where(predicates);

                TypedQuery<DiscountChangeLog> query = entityManager.createQuery(cq);
                return query.getResultList();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
                throw new DatabaseException(ex.getCause().getMessage(), ex);
            } finally {
                entityManager.close();
            }
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public long getCount(Long discountId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> cq = cb.createQuery(Long.class);
                Root<DiscountChangeLog> d = cq.from(DiscountChangeLog.class);

                cq.select(cb.count(d));

                Predicate[] predicates = getPredicates(discountId, cb, d);
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
     * @param discountId discount id
     * @param cb criteria builder
     * @param log discount change log
     * @return
     */
    private Predicate[] getPredicates(Long discountId, CriteriaBuilder cb, Root<DiscountChangeLog> log) {

        Set<Predicate> restrictions = new HashSet();

        if (discountId != null) {
            Path id = log.get("id").get("discountId");
            restrictions.add(cb.equal(id, discountId));
        }

        return restrictions.toArray(new Predicate[restrictions.size()]);
    }

}
