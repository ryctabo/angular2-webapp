package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.QCR;
import com.nativapps.arpia.database.exception.DatabaseException;
import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.Path;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class QCRDaoController extends EntityDao<QCR, QCR.QCRPK> implements QCRDao {

    private static final Logger LOG = Logger.getLogger(QCRDaoController.class.getName());

    private static final QCRDaoController INSTANCE = new QCRDaoController();

    private QCRDaoController() {
        super(QCR.class);
    }

    public static QCRDaoController getInstance() {
        return INSTANCE;
    }

    /**
     * Get all predicates from the given parameters.
     *
     * @param customerId the customer ID
     * @param state      QCR status
     * @param cb         criteria builder
     * @param qcr        structure of QCR
     * @return predicates or null if predicates is empty.
     */
    private Predicate[] getPredicates(Long customerId, Long messengerId, Calendar from, Calendar to, QCR.Status state, CriteriaBuilder cb, Root<QCR> qcr) {
        Set<Predicate> predicates = new HashSet<>();
        if (customerId != null)
            predicates.add(cb.equal(qcr.get("customer").get("id"), customerId));
        if (messengerId != null)
            predicates.add(cb.equal(qcr.get("messenger").get("id"), messengerId));
        
        Path<Calendar> openingDate = qcr.get("openingDate");
        if (from != null)
            predicates.add(cb.greaterThanOrEqualTo(openingDate, from));
        if (to != null)
            predicates.add(cb.lessThanOrEqualTo(openingDate, to));
        if (state != null)
            predicates.add(cb.equal(qcr.get("status"), state));

        return predicates.isEmpty() ? null : predicates.toArray(new Predicate[predicates.size()]);
    }

    @Override
    public List<QCR> findAll(int start, int size, Long customerId, Long messengerId, Calendar from, Calendar to, QCR.Status state) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<QCR> cq = cb.createQuery(QCR.class);

                Root<QCR> qcr = cq.from(QCR.class);

                Predicate[] predicates = getPredicates(customerId, messengerId, from, to, state, cb, qcr);
                if (predicates != null)
                    cq.where(predicates);

                TypedQuery<QCR> query = entityManager.createQuery(cq);
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
    public long count(Long customerId, Long messengerId, Calendar from, Calendar to, QCR.Status state) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<QCR> qcr = query.from(QCR.class);

                query.select(cb.count(qcr));

                Predicate[] predicates = getPredicates(customerId, messengerId, from, to, state, cb, qcr);
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
