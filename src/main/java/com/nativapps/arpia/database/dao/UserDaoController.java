package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.User;
import com.nativapps.arpia.database.entity.UserType;
import com.nativapps.arpia.database.exception.IncorrectCredentialsException;
import com.nativapps.arpia.model.OrderType;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.1.0
 */
public class UserDaoController extends EntityDao<User, Long> implements UserDao {

    private static final Logger LOG = Logger.getLogger(UserDaoController.class.getName());

    private static final UserDaoController INSTANCE = new UserDaoController();

    private UserDaoController() {
        super(User.class);
        if (find(1L) == null)
            createSuperUser();
    }

    public static UserDaoController getInstance() {
        return INSTANCE;
    }

    /**
     * Create super user in the database.
     */
    private void createSuperUser() {
        User superUser = new User();

        superUser.setUsername("admin");
        superUser.setEmail("admin@arpia.com");
        superUser.setDisplayName("Administrador");
        superUser.setPassword("@rp!a");
        superUser.setUrlPic("default");
        superUser.setType(UserType.SUPER_USER);

        this.save(superUser);
    }

    /**
     * Get predicates from the parameter provider.
     *
     * @param cb     criteria builder
     * @param u      structure table
     * @param type   user type
     * @param search value to search
     * @return predicates
     */
    private Predicate[] getPredicates(CriteriaBuilder cb, Root<User> u, UserType type, String search) {
        Set<Predicate> predicates = new HashSet<>();

        if (search != null) {
            Path<String> pa1 = u.get("email");
            Path<String> pa2 = u.get("displayName");
            Path<String> pa3 = u.get("username");

            Predicate p1 = cb.like(pa1, "%" + search + "%");
            Predicate p2 = cb.like(pa2, "%" + search + "%");
            Predicate p3 = cb.like(pa3, "%" + search + "%");

            predicates.add(cb.or(p1, p2, p3));
        }
        if (type != null)
            predicates.add(cb.equal(u.get("type"), type));

        return predicates.isEmpty() ? null : predicates.toArray(new Predicate[predicates.size()]);
    }

    @Override
    public List<User> findAll(UserType type, String search, int start, int size, String orderBy, OrderType orderType) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<User> cq = cb.createQuery(User.class);
                Root<User> u = cq.from(User.class);

                Predicate[] predicates = getPredicates(cb, u, type, search);
                if (predicates != null)
                    cq.where(predicates);

                Path propOrderBy = u.get("id");
                if (orderBy != null)
                    propOrderBy = u.get(orderBy);

                Order order;
                if (orderType == null || orderType == OrderType.ASC)
                    order = cb.asc(propOrderBy);
                else
                    order = cb.desc(propOrderBy);

                cq.orderBy(order);

                TypedQuery<User> query = entityManager.createQuery(cq);
                return query.setFirstResult(start)
                        .setMaxResults(size)
                        .getResultList();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);

            } finally {
                entityManager.close();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public long count(UserType type, String search) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<User> u = cq.from(User.class);

            cq.select(cb.count(u));

            Predicate[] predicates = getPredicates(cb, u, type, search);
            if (predicates != null)
                cq.where(predicates);

            TypedQuery<Long> query = entityManager.createQuery(cq);
            return query.getSingleResult();
        }
        return -1;
    }

    @Override
    public User findByUsername(String username) {
        return executeNamedQuery("user.findByUsername",
                new Parameter("username", username));
    }

    @Override
    public User findByEmail(String email) {
        return executeNamedQuery("user.findByEmail",
                new Parameter("email", email));
    }

    @Override
    public User search(String data) {
        return executeNamedQuery("user.search",
                new Parameter("data", data));
    }

    @Override
    public User login(String userData, String password) throws IncorrectCredentialsException {
        User user = executeNamedQuery("user.login",
                new Parameter("dataUser", userData),
                new Parameter("password", password));

        if (user == null)
            throw new IncorrectCredentialsException("Username or password is invalid.");

        return user;
    }

}
