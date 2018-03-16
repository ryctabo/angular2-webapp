package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Payment;
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
public class PaymentDaoController extends EntityDao<Payment, Long> implements PaymentDao {

    public static final Logger LOG = Logger
            .getLogger(PaymentDaoController.class.getName());

    public static final PaymentDaoController INSTANCE = new PaymentDaoController();

    public static PaymentDaoController getInstance() {
        return INSTANCE;
    }

    public PaymentDaoController() {
        super(Payment.class);
    }

    @Override
    public Double getRealValue(Long debtId, Double value) {
        Double realValue = null;
        Double paymentSum = 0.0;

        for (Payment payment : findAll(debtId))
            paymentSum += payment.getPayment();

        realValue = value - paymentSum;
        return realValue;
    }

    @Override
    public List<Payment> findAll(Long debtId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {

                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Payment> cq = cb.createQuery(Payment.class);
                Root<Payment> p = cq.from(Payment.class);

                Predicate[] predicates = getPredicates(debtId, cb, p, null, null);

                if (predicates != null)
                    cq.where(predicates);

                TypedQuery<Payment> query = entityManager.createQuery(cq);
                return query.getResultList();
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
    public List<Payment> findAll(Long debtId, Calendar from, Calendar to, int start, int size, String orderBy, OrderType type) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {

                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Payment> cq = cb.createQuery(Payment.class);
                Root<Payment> p = cq.from(Payment.class);

                Predicate[] predicates = getPredicates(debtId, cb, p, from, to);

                if (predicates != null)
                    cq.where(predicates);

                Path propOrderBy = p.get("id");
                if (orderBy != null)
                    propOrderBy = p.get(orderBy);

                Order order;
                if (type == null || type == OrderType.DESC)
                    order = cb.desc(propOrderBy);
                else
                    order = cb.asc(propOrderBy);
                cq.orderBy(order);

                TypedQuery<Payment> query = entityManager.createQuery(cq);
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
    public long getCount(Long debtId, Calendar from, Calendar to) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> cq = cb.createQuery(Long.class);
                Root<Payment> p = cq.from(Payment.class);

                cq.select(cb.count(p));

                Predicate[] predicates = getPredicates(debtId, cb, p, from, to);

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
     * @param debtId debt ID
     * @param cb criteria builder
     * @param p entity structure
     * @param from start date
     * @param to end date
     * @return restrictions array
     */
    private Predicate[] getPredicates(Long debtId, CriteriaBuilder cb,
            Root<Payment> p, Calendar from, Calendar to) {

        Set<Predicate> restrictions = new HashSet<>();

        if (debtId != null) {
            Path id = p.get("id").get("debtId");
            restrictions.add(cb.equal(id, debtId));
        }

        Path<Calendar> sd = p.get("date");

        if (from != null)
            restrictions.add(cb.greaterThanOrEqualTo(sd, from));

        if (to != null)
            restrictions.add(cb.lessThanOrEqualTo(sd, to));

        return restrictions.isEmpty() ? null : restrictions.toArray(new Predicate[restrictions.size()]);
    }

}
