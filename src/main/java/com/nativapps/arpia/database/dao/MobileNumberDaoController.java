package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.MobileNumber;
import com.nativapps.arpia.database.exception.DatabaseException;
import com.nativapps.arpia.model.OrderType;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 *
 * @version 2.0
 */
public class MobileNumberDaoController extends EntityDao<MobileNumber, MobileNumber.MobileNumberPK>
        implements MobileNumberDao {

    private static final Logger LOG = Logger
            .getLogger(ParticularDaoController.class.getName());

    private static final MobileNumberDaoController INSTANCE
            = new MobileNumberDaoController();

    private MobileNumberDaoController() {
        super(MobileNumber.class);
    }

    public static MobileNumberDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public MobileNumber findLast(long operationId) {
        List<MobileNumber> mNumbers = executeNamedQueryForList(
                "mobileNumber.lastByOperationId", 0, 1,
                new Parameter("id", operationId));
        return mNumbers == null || mNumbers.isEmpty() ? null : mNumbers.get(0);
    }

    @Override
    public List<MobileNumber> findAll(Long operationId, Boolean available,
            int start, int size, String orderBy, OrderType orderType) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery cq = cb.createQuery();
                Root<MobileNumber> root = cq.from(MobileNumber.class);
                cq.select(root);

                //Create predicates if they exist
                Set<Predicate> predicates = new HashSet<>();
                if (operationId != null) {
                    Path<Long> p = root.get("operation").get("id");
                    predicates.add(cb.equal(p, operationId));
                }
                if (available != null) {
                    Path prop = root.get("messenger");
                    Predicate p = available ? cb.isNull(prop) : cb.isNotNull(prop);
                    predicates.add(p);
                }

                //verify if exists predicates
                if (!predicates.isEmpty())
                    cq.where(predicates.toArray(new Predicate[predicates.size()]));

                if (orderBy != null) {
                    Order order;
                    if (orderType == null || orderType == OrderType.ASC) {
                        order = cb.asc(root.get(orderBy));
                    } else {
                        order = cb.desc(root.get(orderBy));
                    }
                    cq.orderBy(order);
                }

                //create query and get result
                TypedQuery<MobileNumber> query = entityManager.createQuery(cq);
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
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean save(MobileNumber... mobileNumbers) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                EntityTransaction transaction = entityManager.getTransaction();
                transaction.begin();
                for (MobileNumber mn : mobileNumbers)
                    entityManager.merge(mn);
                transaction.commit();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
                throw new DatabaseException(ex.getCause().getMessage(), ex);
            } finally {
                entityManager.close();
            }
        }
        return false;
    }

}
