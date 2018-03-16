package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Domicile;
import com.nativapps.arpia.database.entity.DomicileExecute;
import com.nativapps.arpia.database.exception.DatabaseException;
import com.nativapps.arpia.model.OrderType;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public class DomicileDaoController extends EntityDao<Domicile, Long> implements DomicileDao {

    private static final Logger LOG = Logger.getLogger(DomicileDaoController.class.getName());

    private static final DomicileDaoController INSTANCE = new DomicileDaoController();

    private DomicileDaoController() {
        super(Domicile.class);
    }

    public static DomicileDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Domicile> findByCustomerId(long customerId) {
        return executeNamedQueryForList("domicile.findByCustomerId",
                new Parameter("id", customerId));
    }

    /**
     * Get restrictions from the given data.
     *
     * @param cb          criteria builder
     * @param d           domicile structure
     * @param customerId  the customer ID
     * @param operationId the operation ID
     * @param date        register date
     * @return restrictions
     */
    private Predicate[] getPredicates(CriteriaBuilder cb, Root<Domicile> d,
                                      Long customerId, Long operationId, Calendar date) {
        Set<Predicate> predicates = new HashSet<>();

        if (customerId != null)
            predicates.add(cb.equal(d.get("customerId"), customerId));
        if (operationId != null)
            predicates.add(cb.equal(d.get("operationId"), operationId));
        if (date != null)
            predicates.add(cb.equal(d.get("createDate"), date));

        return predicates.isEmpty() ? null : predicates
                .toArray(new Predicate[predicates.size()]);
    }

    @Override
    public List<Domicile> findAll(Long customerId, Long operationId,
                                  Calendar date, int start, int size, String orderBy, OrderType type) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Domicile> cq = cb.createQuery(Domicile.class);
                Root<Domicile> d = cq.from(Domicile.class);

                Predicate[] predicates = getPredicates(cb, d, customerId,
                        operationId, date);
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

                TypedQuery<Domicile> query = entityManager.createQuery(cq);
                return query.setFirstResult(start)
                        .setMaxResults(size)
                        .getResultList();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
                throw new DatabaseException(ex.getCause().getMessage(), ex);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<Object[]> frequent(Long customerId, int start, int size) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

                Root de = cq.from(DomicileExecute.class);
                Join d = de.join("domicile");
                d.on(cb.equal(de.get("domicile").get("id"), d.get("id")));

                cq.multiselect(d, cb.count(de));

                if (customerId != null) {
                    cq.where(cb.equal(d.get("customerId"), customerId));
                }

                cq.groupBy(d.get("id"));
                cq.orderBy(cb.desc(cb.count(de)));
                TypedQuery<Object[]> query = entityManager.createQuery(cq);
                return query.setMaxResults(size)
                        .setFirstResult(start)
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
    public Long findAllCount(Long customerId, Long operationId, Calendar date) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);

                Root<Domicile> d = query.from(Domicile.class);
                query.select(cb.count(d));

                Predicate[] predicates = getPredicates(cb, d, customerId,
                        operationId, date);
                if (predicates != null)
                    query.where(predicates);

                return entityManager.createQuery(query).getSingleResult();
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
