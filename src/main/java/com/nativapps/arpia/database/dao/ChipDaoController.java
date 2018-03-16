package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Chip;
import com.nativapps.arpia.database.entity.ChipType;
import com.nativapps.arpia.database.exception.DatabaseException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class ChipDaoController extends EntityDao<Chip, Long> implements ChipDao {

    private static final Logger LOG = Logger.getLogger(ChipDaoController.class.getName());

    private static final ChipDaoController INSTANCE = new ChipDaoController();

    private ChipDaoController() {
        super(Chip.class);
    }

    public static ChipDaoController getInstance() {
        return INSTANCE;
    }

    /**
     * Get restrictions.
     *
     * @param cb          criteria builder
     * @param c           structure of chip
     * @param messengerId the messenger ID
     * @param type        chip type
     * @return predicates array
     */
    private Predicate[] getWhere(CriteriaBuilder cb, Root<Chip> c,
                                 Long messengerId, ChipType type) {
        List<Predicate> p = new ArrayList<>();
        if (messengerId != null)
            p.add(cb.equal(c.get("messenger").get("id"), messengerId));
        if (type != null)
            p.add(cb.equal(c.get("type"), type));
        return p.isEmpty() ? null : p.toArray(new Predicate[p.size()]);
    }

    @Override
    public List<Chip> findAll(Long messengerId, ChipType type, int start, int size) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Chip> cq = cb.createQuery(Chip.class);

                Root<Chip> c = cq.from(Chip.class);

                Predicate[] predicates = getWhere(cb, c, messengerId, type);
                if (predicates != null)
                    cq.where(predicates);

                cq.orderBy(cb.desc(c.get("id")));

                TypedQuery<Chip> query = entityManager.createQuery(cq);
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
    public long count(Long messengerId, ChipType type) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<Chip> c = query.from(Chip.class);

                query.select(cb.count(c));

                Predicate[] predicates = getWhere(cb, c, messengerId, type);
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
