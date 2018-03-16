package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Evaluation;
import com.nativapps.arpia.database.exception.DatabaseException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1
 */
public class EvaluationDaoController extends EntityDao<Evaluation, Long> implements EvaluationDao {

    private static final Logger LOG = Logger.getLogger(EvaluationDaoController.class.getName());

    private static final EvaluationDaoController INSTANCE = new EvaluationDaoController();

    private EvaluationDaoController() {
        super(Evaluation.class);
    }

    public static EvaluationDaoController getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public List<Evaluation> findAll(int start, int size, Long messengerId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Evaluation> cq = cb.createQuery(Evaluation.class);
                Root<Evaluation> me = cq.from(Evaluation.class);

                if (messengerId != null)
                    cq.where(cb.equal(me.get("messenger").get("id"), messengerId));

                TypedQuery<Evaluation> query = entityManager.createQuery(cq);

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
    public long findAllCount(Long messengerId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<Evaluation> me = query.from(Evaluation.class);

                query.select(cb.count(me));

                if (messengerId != null)
                    query.where(cb.equal(me.get("messenger").get("id"), messengerId));

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
