package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.MapPoint;
import com.nativapps.arpia.database.exception.DatabaseException;
import com.nativapps.arpia.model.OrderType;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class MapPointDaoController extends EntityDao<MapPoint, Long> implements MapPointDao {

    private static final MapPointDaoController INSTANCE = new MapPointDaoController();

    private static final Logger LOG = Logger.getLogger(MapPointDaoController.class.getName());

    private MapPointDaoController() {
        super(MapPoint.class);
    }

    public static MapPointDaoController getInstance() {
        return INSTANCE;
    }

    /**
     * Get predicates for filtered list.
     *
     * @param cb     criteria builder
     * @param mp     structure of map point
     * @param search value to search
     * @return predicates.
     */
    private Predicate getPredicates(CriteriaBuilder cb, Root<MapPoint> mp, String search) {
        Predicate predicate = null;
        if (search != null) {
            Path<String> propName = mp.get("name");
            predicate = cb.like(propName, "%" + search + "%");
        }
        return predicate;
    }

    @Override
    public List<MapPoint> findAll(String search, int start, int size, String orderBy, OrderType type) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<MapPoint> cq = cb.createQuery(MapPoint.class);
                Root<MapPoint> mp = cq.from(MapPoint.class);

                Predicate predicate = getPredicates(cb, mp, search);
                if (predicate != null)
                    cq.where(predicate);

                Path<Long> propToOrder = mp.get("id");
                if (orderBy != null)
                    propToOrder = mp.get(orderBy);

                Order order;
                if (type == null || type == OrderType.ASC)
                    order = cb.asc(propToOrder);
                else
                    order = cb.desc(propToOrder);
                cq.orderBy(order);

                TypedQuery<MapPoint> query = entityManager.createQuery(cq);
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
    public long count(String search) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<MapPoint> mp = query.from(MapPoint.class);

                query.select(cb.count(mp));

                Predicate predicate = getPredicates(cb, mp, search);
                if (predicate != null)
                    query.where(predicate);

                return entityManager.createQuery(query).getSingleResult();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
                throw new DatabaseException(ex.getCause().getMessage(), ex);
            } finally {
                entityManager.close();
            }
        }
        return -1;
    }

}
