package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Stock;
import com.nativapps.arpia.database.entity.StockType;
import com.nativapps.arpia.database.exception.DatabaseException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class StockDaoController extends EntityDao<Stock, Stock.StockPK> implements StockDao {

    private static final Logger LOG = Logger.getLogger(StockDaoController.class.getName());

    private static final StockDaoController INSTANCE = new StockDaoController();

    private StockDaoController() {
        super(Stock.class);
    }

    public static StockDaoController getInstance() {
        return INSTANCE;
    }

    /**
     * Get predicates from the given parameters.
     * <p>If all parameters is null, then this method return null.</p>
     *
     * @param inventoryId the inventory element ID
     * @param type        stock type, can be INPUT or OUTPUT
     * @param startDate   initial date
     * @param endDate     final date
     * @param cb          criteria builder
     * @param stock       stock structure
     * @return array of predicates
     */
    private Predicate[] getPredicates(Long inventoryId, StockType type, Calendar startDate,
                                      Calendar endDate, CriteriaBuilder cb, Root<Stock> stock) {
        Set<Predicate> prs = new HashSet<>();

        if (inventoryId != null)
            prs.add(cb.equal(stock.get("inventory").get("id"), inventoryId));
        if (type != null)
            prs.add(cb.equal(stock.get("type"), type));

        if (startDate != null) {
            Path<Calendar> created = stock.get("registerDate");
            prs.add(cb.greaterThanOrEqualTo(created, startDate));
        }
        if (endDate != null) {
            Path<Calendar> created = stock.get("registerDate");
            prs.add(cb.lessThanOrEqualTo(created, endDate));
        }

        return prs.isEmpty() ? null : prs.toArray(new Predicate[prs.size()]);
    }

    @Override
    public List<Stock> findAll(Long inventoryId, StockType type, Calendar startDate,
                               Calendar endDate, int start, int size) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Stock> cq = cb.createQuery(Stock.class);

                Root<Stock> stock = cq.from(Stock.class);

                Predicate[] predicates = getPredicates(inventoryId, type,
                        startDate, endDate, cb, stock);
                if (predicates != null)
                    cq.where(predicates);

                TypedQuery<Stock> query = entityManager.createQuery(cq);
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
    public long count(Long inventoryId, StockType type, Calendar startDate, Calendar endDate) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<Stock> stock = query.from(Stock.class);

                query.select(cb.count(stock));

                Predicate[] predicates = getPredicates(inventoryId, type,
                        startDate, endDate, cb, stock);
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
