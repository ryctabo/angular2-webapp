package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.SecurityDeposit;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.database.entity.UserType;
import com.nativapps.arpia.database.exception.DatabaseException;
import com.nativapps.arpia.model.OrderType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class SecurityDepositDaoController extends EntityDao<SecurityDeposit, Long>
        implements SecurityDepositDao {

    private final static SecurityDepositDaoController INSTANCE
            = new SecurityDepositDaoController();
    private static final Logger LOG = Logger.getLogger(
            SecurityDepositDaoController.class.getName());

    private SecurityDepositDaoController() {
        super(SecurityDeposit.class);
    }

    public static SecurityDepositDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public double paidToDate(Long ownerId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            TypedQuery<Double> query = entityManager
                    .createNamedQuery("securityDeposit.paidOut", Double.class);
            query.setParameter("ownerId", ownerId);
            Double sum = query.getSingleResult();
            return sum == null ? -1 : sum;
        }
        return -1;
    }

    @Override
    public List<User> findAll(String search, int start, int size,
            String orderBy, OrderType orderType) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<User> cq
                        = cb.createQuery(User.class);
                Root<User> m = cq.from(User.class);

                Set<Predicate> predicates = new HashSet<>();
                if (search != null) {
                    Path pa1 = m.get("displayName");

                    Predicate p1 = cb.like(pa1, "%" + search + "%");
                    predicates.add(cb.or(p1));
                }
                if (!predicates.isEmpty()) {
                    cq.where(predicates.toArray(new Predicate[predicates.size()]));
                }

                Path propOrderBy = m.get("id");
                if (orderBy != null) {
                    propOrderBy = m.get(orderBy);
                }

                Order order = orderType != null || orderType == OrderType.DESC
                        ? cb.desc(propOrderBy) : cb.asc(propOrderBy);
                cq.orderBy(order);

                TypedQuery<User> query
                        = entityManager.createQuery(cq);

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
    public long getCountPayments() {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                return ((Long) entityManager
                        .createNamedQuery("securityDeposit.count")
                        .getSingleResult());
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
    public List<SecurityDeposit> findAllPayments(String search, int start, int size,
            Date from, Date to,
            String orderBy, OrderType orderType) {

        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<SecurityDeposit> cq
                        = cb.createQuery(SecurityDeposit.class);
                Root<SecurityDeposit> m = cq.from(SecurityDeposit.class);

                Set<Predicate> predicates = new HashSet<>();
                if (getRestrictions(entityManager, m, from, to) != null) {
                    predicates.add(getRestrictions(entityManager, m, from, to));
                }
                if (search != null) {
                    Path pa1 = m.get("owner").get("displayName");

                    Predicate p1 = cb.like(pa1, "%" + search + "%");
                    predicates.add(cb.or(p1));
                }

                if (!predicates.isEmpty()) {
                    cq.where(predicates.toArray(new Predicate[predicates.size()]));
                }

                Path propOrderBy = m.get("id");
                if (orderBy != null) {
                    propOrderBy = m.get(orderBy);
                }

                Order order = orderType != null || orderType == OrderType.DESC
                        ? cb.desc(propOrderBy) : cb.asc(propOrderBy);
                cq.orderBy(order);

                TypedQuery<SecurityDeposit> query
                        = entityManager.createQuery(cq);

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

    private Predicate getRestrictions(EntityManager manager,
            Root<SecurityDeposit> root, Date from, Date to) {

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        Path<Date> registerDate = root.get("created");
        List<Predicate> res = new ArrayList<>();
        if (from != null) {
            res.add(cb.greaterThanOrEqualTo(registerDate, from));
        }
        if (to != null) {
            res.add(cb.lessThan(registerDate, to));
        }

        return res.isEmpty() ? null
                : cb.and(res.toArray(new Predicate[res.size()]));
    }

}
