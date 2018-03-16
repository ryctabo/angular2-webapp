package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Discount;
import com.nativapps.arpia.database.entity.DiscountChangeLog;
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
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.3.0
 */
public class DiscountDaoController extends EntityDao<Discount, Long> implements DiscountDao {

    public static final Logger LOG = Logger.getLogger(DiscountDaoController.class.getName());

    public static final DiscountDaoController INSTANCE = new DiscountDaoController();

    private DiscountDaoController() {
        super(Discount.class);
    }

    public static DiscountDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public Discount saveDiscountAndLog(Discount discount, DiscountChangeLog log) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                entityManager.getTransaction().begin();
                entityManager.merge(log);
                Discount entity = entityManager.merge(discount);
                entityManager.getTransaction().commit();
                return entity;
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
                throw new DatabaseException(ex.getCause().getMessage(), ex);
            } finally {
                entityManager.close();
            }
        }
        return null;
    }

    @Override
    public List<Discount> findAll(String search, Boolean active, int start, int size, String orderBy, OrderType type) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Discount> cq = cb.createQuery(Discount.class);
                Root<Discount> d = cq.from(Discount.class);

                Predicate[] predicates = getPredicates(search, cb, d, active);
                if (predicates != null)
                    cq.where(predicates);

                Path<Long> propOrderBy = d.get("id");
                if (orderBy != null)
                    propOrderBy = d.get(orderBy);

                Order order;
                if (type == null || type == OrderType.DESC)
                    order = cb.desc(propOrderBy);
                else
                    order = cb.asc(propOrderBy);
                cq.orderBy(order);

                TypedQuery<Discount> query = entityManager.createQuery(cq);
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
    public long getCount(String search, Boolean active) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> cq = cb.createQuery(Long.class);
                Root<Discount> d = cq.from(Discount.class);

                cq.select(cb.count(d));

                Predicate[] predicates = getPredicates(search, cb, d, active);
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

    @Override
    public int getCountChanges(long discountId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<DiscountChangeLog> d = query.from(DiscountChangeLog.class);

            query.select(cb.count(d));
            query.where(cb.equal(d.get("discount").get("id"), discountId));

            return entityManager.createQuery(query)
                    .getSingleResult()
                    .intValue();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            entityManager.close();
        }
        return -1;
    }

    @Override
    public int getCountUsed(long discountId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);

            Root<DomicileExecute> de = query.from(DomicileExecute.class);
            Join<DomicileExecute, Discount> d = de.join("discount");
            d.on(cb.equal(de.get("discount").get("id"), d.get("id")));

            query.select(cb.count(de));
            query.where(cb.equal(d.get("id"), discountId));

            return entityManager.createQuery(query)
                    .getSingleResult()
                    .intValue();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            entityManager.close();
        }
        return -1;
    }

    /**
     * Get restrictions from the given data.
     *
     * @param search search param
     * @param cb     criteria builder
     * @param d      discount structure
     * @param active true, if you need filter list with discount active
     * @return restrictions
     */
    private Predicate[] getPredicates(String search, CriteriaBuilder cb, Root<Discount> d, Boolean active) {
        Set<Predicate> restrictions = new HashSet<>();
        if (search != null) {
            Path<String> name = d.get("name");
            restrictions.add(cb.like(name, "%" + search + "%"));
        }
        if (active != null) {
            Path<Calendar> start = d.get("startDate");
            Path<Calendar> end = d.get("endDate");
            Calendar now = Calendar.getInstance();
            if (active) {
                Predicate p1 = cb.greaterThanOrEqualTo(start, now);
                Predicate p2 = cb.lessThanOrEqualTo(end, now);
                restrictions.add(cb.and(p1, p2));
            } else {
                Predicate p1 = cb.lessThan(start, now);
                Predicate p2 = cb.greaterThan(end, now);
                restrictions.add(cb.or(p1, p2));
            }
        }
        return restrictions.isEmpty() ? null : restrictions.toArray(new Predicate[restrictions.size()]);
    }

}
