package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Penalty;
import com.nativapps.arpia.database.exception.DatabaseException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Cristobal Romero Rossi <cristobalromerorossi@gmail.com>
 * @version 1.0
 */
public class PenaltyDaoController extends EntityDao<Penalty, Long>
        implements PenaltyDao {

    private static final Logger LOG = Logger
            .getLogger(PenaltyDaoController.class.getName());

    private final static PenaltyDaoController INSTANCE
            = new PenaltyDaoController();

    public PenaltyDaoController() {
        super(Penalty.class);
    }

    public static PenaltyDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Penalty> findAll(Long messengerId, int start, int size) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Penalty> cq = cb.createQuery(Penalty.class);
                Root<Penalty> p = cq.from(Penalty.class);

                if (messengerId != null)
                    cq.where(cb.equal(p.get("messenger").get("id"), messengerId));

                TypedQuery<Penalty> query = entityManager.createQuery(cq);
                return query.setFirstResult(start)
                        .setMaxResults(size)
                        .getResultList();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
                throw new DatabaseException(ex.getCause().getMessage(), ex);
            }
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public long findAllCount(Long messengerId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Penalty> p = cq.from(Penalty.class);

            if (messengerId != null)
                cq.where(cb.equal(p.get("messenger").get("id"), messengerId));

            return entityManager.createQuery(cq).getSingleResult();
        }
        return -1L;
    }
}
