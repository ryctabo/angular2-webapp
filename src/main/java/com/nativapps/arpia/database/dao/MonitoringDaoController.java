package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Monitoring;
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
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class MonitoringDaoController extends EntityDao<Monitoring, Long>
        implements MonitoringDao {

    private static final Logger LOG = Logger
            .getLogger(MonitoringDaoController.class.getName());

    private static final MonitoringDaoController INSTANCE
            = new MonitoringDaoController();

    private MonitoringDaoController() {
        super(Monitoring.class);
    }

    public static MonitoringDaoController getInstance() {
        return INSTANCE;
    }

    /**
     * Get restrictions from the given data.
     *
     * @param cb criteria builder
     * @param m monitoring structure
     * @param startDate start date
     * @param endDate end date
     * @param domicileId domicile ID
     *
     * @return list of predicates
     */
    private Predicate[] getPredicates(CriteriaBuilder cb, Root<Monitoring> m,
            Calendar startDate, Calendar endDate, Long domicileId) {
        Set<Predicate> restrictions = new HashSet<>();

        if (domicileId != null)
            restrictions.add(cb.equal(m.get("domicileId"), domicileId));

        Path<Calendar> sd = m.get("registerDate");

        if (startDate != null)
            restrictions.add(cb.greaterThanOrEqualTo(sd, startDate));

        if (endDate != null)
            restrictions.add(cb.lessThanOrEqualTo(sd, endDate));

        return restrictions.toArray(new Predicate[restrictions.size()]);
    }

    @Override
    public List<Monitoring> findAll(Calendar startDate, Calendar endDate,
            Long domicileId, int start, int size, String orderBy, OrderType type) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Monitoring> cq = cb.createQuery(Monitoring.class);
                Root<Monitoring> m = cq.from(Monitoring.class);

                Predicate[] predicates = getPredicates(cb, m, startDate, endDate, domicileId);
                if (predicates != null)
                    cq.where(predicates);

                Path<Object> propOrderBy = m.get("id");
                if (orderBy != null)
                    propOrderBy = m.get(orderBy);

                Order order;
                if (type == null || type == OrderType.DESC)
                    order = cb.desc(propOrderBy);
                else
                    order = cb.asc(propOrderBy);
                cq.orderBy(order);

                TypedQuery<Monitoring> query = entityManager.createQuery(cq);
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
        return Collections.EMPTY_LIST;
    }

    @Override
    public long getCount(Calendar startDate, Calendar endDate, Long domicileId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> cq = cb.createQuery(Long.class);
                Root<Monitoring> m = cq.from(Monitoring.class);

                cq.select(cb.count(m));

                Predicate[] predicates = getPredicates(cb, m, startDate, endDate, domicileId);
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

}
