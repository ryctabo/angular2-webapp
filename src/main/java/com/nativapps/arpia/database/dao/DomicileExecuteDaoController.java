package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.*;
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
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.5.0
 */
public class DomicileExecuteDaoController extends EntityDao<DomicileExecute, Long> implements DomicileExecuteDao {

    private static final Logger LOG = Logger.getLogger(DomicileExecuteDaoController.class.getName());

    private static final DomicileExecuteDaoController INSTANCE = new DomicileExecuteDaoController();

    private DomicileExecuteDaoController() {
        super(DomicileExecute.class);
    }

    public static DomicileExecuteDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<DomicileExecute> findByCustomerId(long customerId) {
        return executeNamedQueryForList("execute.findByCustomerId",
                new Parameter("id", customerId));
    }

    /**
     * Get predicates from the given parameters.
     *
     * @param cb             criteria builder
     * @param d              domicile execute structure
     * @param state          state of domicile
     * @param startDate      start date
     * @param endDate        end date
     * @param customerId     the customer ID
     * @param customerGender the customer gender
     * @param auto           true, if the domicile has automatic assignment
     * @param operatorId     the operator ID
     * @param dispatcherId   the dispatcher ID
     * @param operationId    the operation ID
     * @param canceled       true, if the domicile is canceled
     * @return restrictions
     */
    private Predicate[] getPredicates(CriteriaBuilder cb, Root<DomicileExecute> d, DomicileStatus state, Calendar startDate,
                                      Calendar endDate, Long customerId, Gender customerGender, Boolean auto,
                                      Long operatorId, Long dispatcherId, Long operationId, Boolean canceled) {
        Set<Predicate> predicates = new HashSet<>();

        if (startDate != null && endDate != null) {
            Path<Calendar> prop = d.get("startDate");
            predicates.add(cb.between(prop, startDate, endDate));
        }

        if (state != null)
            predicates.add(cb.equal(d.get("status"), state));
        if (customerId != null)
            predicates.add(cb.equal(d.get("domicile").get("customerId"), customerId));
        if (customerGender != null)
            predicates.add(cb.equal(d.get("domicile").get("customer").get("particular").get("gender"), customerGender));
        if (auto != null)
            predicates.add(cb.equal(d.get("automatic"), auto));
        if (operatorId != null)
            predicates.add(cb.equal(d.get("user").get("id"), operatorId));

        if (dispatcherId != null) {
            Join<DomicileExecute, Assignment> a = d.join("assignments");
            predicates.add(cb.equal(a.get("user").get("id"), dispatcherId));
        }

        if (operationId != null)
            predicates.add(cb.equal(d.get("domicile").get("operationId"), operationId));

        if (canceled != null) {
            Path<Object> prop = d.get("cancelDate");
            predicates.add(canceled ? cb.isNotNull(prop) : cb.isNull(prop));
        }

        return predicates.isEmpty() ? null : predicates
                .toArray(new Predicate[predicates.size()]);
    }

    @Override
    public List<DomicileExecute> findAll(DomicileStatus state, Calendar startDate, Calendar endDate, Long customerId,
                                         Gender customerGender, Boolean auto, Long operatorId, Long dispatcherId,
                                         Long operationId, Boolean canceled, int start, int size, String orderBy,
                                         OrderType orderType) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<DomicileExecute> cq = cb.createQuery(DomicileExecute.class);
                Root<DomicileExecute> d = cq.from(DomicileExecute.class);

                cq.select(d);

                Predicate[] predicates = getPredicates(cb, d, state, startDate, endDate, customerId,
                        customerGender, auto, operatorId, dispatcherId, operationId, canceled);
                if (predicates != null)
                    cq.where(predicates);

                Path propOrderBy = d.get("id");
                if (orderBy != null)
                    propOrderBy = d.get(orderBy);

                Order order;
                if (orderType == null || orderType == OrderType.DESC)
                    order = cb.desc(propOrderBy);
                else
                    order = cb.asc(propOrderBy);
                cq.orderBy(order);

                TypedQuery<DomicileExecute> query = entityManager.createQuery(cq);
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
    public long count(DomicileStatus state, Calendar startDate, Calendar endDate, Long customerId,
                      Gender customerGender, Boolean auto, Long operatorId, Long dispatcherId, Long operationId,
                      Boolean canceled) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);

                Root<DomicileExecute> d = query.from(DomicileExecute.class);
                query.select(cb.count(d));

                Predicate[] predicates = getPredicates(cb, d, state, startDate, endDate, customerId,
                        customerGender, auto, operatorId, dispatcherId, operationId, canceled);
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

    @Override
    public List<DomicileExecute> findAll(Calendar startDate, Calendar endDate, Long messengerId) {
        return executeNamedQueryForList("execute.findByMessengerId",
                new Parameter("messengerId", messengerId),
                new Parameter("startDate", startDate),
                new Parameter("endDate", endDate));
    }

    @Override
    public List<DomicileExecute> findByNeighborhood(long neighborhood, int start, int size) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<DomicileExecute> cq = cb.createQuery(DomicileExecute.class);
                Root<DomicileExecute> de = cq.from(DomicileExecute.class);

                Join<DomicileExecute, Domicile> d = de.join("domicile");
                Join<Domicile, Location> l = d.join("locations");

                cq.where(cb.equal(l.get("neighborhood").get("id"), neighborhood));

                TypedQuery<DomicileExecute> query = entityManager.createQuery(cq);
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
    public long countByNeighborhood(long neighborhood) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<DomicileExecute> de = query.from(DomicileExecute.class);

            query.select(cb.count(de));

            Join<DomicileExecute, Domicile> d = de.join("domicile");
            Join<Domicile, Location> l = d.join("locations");

            query.where(cb.equal(l.get("neighborhood").get("id"), neighborhood));

            return entityManager.createQuery(query)
                    .getSingleResult();
        }
        return -1L;
    }

}
