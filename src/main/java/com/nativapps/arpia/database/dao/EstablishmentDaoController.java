package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Establishment;
import com.nativapps.arpia.database.exception.DatabaseException;
import com.nativapps.arpia.model.OrderType;
import java.util.List;
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
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class EstablishmentDaoController extends EntityDao<Establishment, Long>
        implements EstablishmentDao {

    private static final Logger LOG = Logger
            .getLogger(EstablishmentDaoController.class.getName());

    private static final EstablishmentDaoController INSTANCE
            = new EstablishmentDaoController();

    private EstablishmentDaoController() {
        super(Establishment.class);
    }

    public static EstablishmentDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Establishment> findAll(int start, int size, String search,
            String orderBy, OrderType orderType) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Establishment> query = cb
                    .createQuery(Establishment.class);

            Root<Establishment> e = query.from(Establishment.class);
            query.select(e);

            Predicate restrictions = getRestrictions(manager, e, search);
            if (restrictions != null)
                query.where(restrictions);

            if (orderBy != null) {
                Order order;
                if (orderType == null || orderType == OrderType.ASC)
                    order = cb.asc(e.get(orderBy));
                else
                    order = cb.desc(e.get(orderBy));

                query.orderBy(order);
            }

            TypedQuery<Establishment> typedQuery = manager
                    .createQuery(query);

            return size == 0 ? typedQuery.getResultList() : typedQuery
                    .setMaxResults(size).setFirstResult(start).getResultList();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public Establishment findByCustomerId(Long id) {
        return executeNamedQuery("establishment.findByCustomerId",
                new Parameter("customerId", id));
    }

    @Override
    public long findAllCount(String search) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);

            Root<Establishment> e = query.from(Establishment.class);
            query.select(cb.count(e));

            Predicate restrictions = getRestrictions(manager, e, search);
            if (restrictions != null)
                query.where(restrictions);

            return manager.createQuery(query).getSingleResult();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    private Predicate getRestrictions(EntityManager manager,
            Root<Establishment> e, String search) {
        if (search != null) {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            Path<String> pathNit = e.get("nit");
            Path<String> pathName = e.get("name");

            Predicate p1 = cb.like(pathNit, "%" + search + "%");
            Predicate p2 = cb.like(pathName, "%" + search + "%");

            return cb.or(p1, p2);
        }

        return null;
    }

    @Override
    public Establishment findByNit(String nit) {
        return executeNamedQuery("establishment.findByNit", 
                new Parameter("nit", nit));
    }
}
