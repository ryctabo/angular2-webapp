package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Egress;
import com.nativapps.arpia.database.exception.DatabaseException;
import com.nativapps.arpia.model.OrderType;
import com.nativapps.arpia.rest.bean.EgressType;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @version 1.0.1
 */
public class EgressDaoController extends EntityDao<Egress, Long> implements EgressDao {

    /**
     * {@link EgressDaoController} logger.
     */
    public static final Logger LOG = Logger.getLogger(EgressDaoController.class.getName());

    /**
     * Instance of {@link EgressDaoController}.
     */
    public static final EgressDaoController INSTANCE = new EgressDaoController();

    /**
     * Construct an object of {@link EgressDaoController}.
     */
    private EgressDaoController() {
        super(Egress.class);
    }

    /**
     * Get unique instance of {@link EgressDaoController}.
     *
     * @return instance.
     */
    public static EgressDaoController getInstance() {
        return INSTANCE;
    }

    /**
     * Get restrictions from the given data.
     *
     * @param search     search param
     * @param cb         criteria builder
     * @param e          egress structure
     * @param from       start date
     * @param to         end date
     * @param userId     the egress user id
     * @param closured   the egress closure state
     * @param egressType the egress type
     * @return restrictions array
     */
    private Predicate[] getPredicates(String search, CriteriaBuilder cb,
                                      Root<Egress> e, Calendar from, Calendar to, Long userId,
                                      Boolean closured, EgressType egressType) {
        Set<Predicate> restrictions = new HashSet<>();
        if (search != null) {
            Path<String> concept = e.get("concept");
            restrictions.add(cb.like(concept, "%" + search + "%"));
        }

        Path<Calendar> created = e.get("created");
        if (from != null)
            restrictions.add(cb.greaterThanOrEqualTo(created, from));
        if (to != null)
            restrictions.add(cb.lessThanOrEqualTo(created, to));

        if (userId != null && userId > 0)
            restrictions.add(cb.equal(e.get("assignedBy").get("id"), userId));

        if (closured != null && closured)
            restrictions.add(cb.isNotNull(e.get("closure")));
        else if (closured != null)
            restrictions.add(cb.isNull(e.get("closure")));

        if (egressType != null) {
            switch (egressType) {
                case CUSTOMER_EGRESS:
                    restrictions.add(cb.equal(e.get("type"), "CE"));
                    break;
                case MANAGEMENT_EGRESS:
                    restrictions.add(cb.equal(e.get("type"), "AE"));
                    break;
                case MESSENGER_EGRESS:
                    restrictions.add(cb.equal(e.get("type"), "ME"));
                    break;
                case OPERATOR_EGRESS:
                    restrictions.add(cb.equal(e.get("type"), "OE"));
            }
        }

        return restrictions.isEmpty() ? null : restrictions.toArray(new Predicate[restrictions.size()]);
    }

    @Override
    public List<Egress> findAll(Long userId, Boolean closureState, EgressType egressType,
                                String search, Calendar from, Calendar to,
                                int start, int size, String orderBy, OrderType type) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Egress> cq = cb.createQuery(Egress.class);
                Root<Egress> e = cq.from(Egress.class);

                Predicate[] predicates = getPredicates(search, cb, e, from, to,
                        userId, closureState, egressType);
                if (predicates != null)
                    cq.where(predicates);

                Path propOrderBy = e.get("id");
                if (orderBy != null)
                    propOrderBy = e.get(orderBy);

                Order order;
                if (type == null || type == OrderType.DESC)
                    order = cb.desc(propOrderBy);
                else
                    order = cb.asc(propOrderBy);
                cq.orderBy(order);

                TypedQuery<Egress> query = entityManager.createQuery(cq);
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
    public long count(Long userId, Boolean closureState, EgressType egressType,
                      String search, Calendar from, Calendar to) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> cq = cb.createQuery(Long.class);
                Root<Egress> d = cq.from(Egress.class);

                cq.select(cb.count(d));

                Predicate[] predicates = getPredicates(search, cb, d, from, to,
                        userId, closureState, egressType);
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
