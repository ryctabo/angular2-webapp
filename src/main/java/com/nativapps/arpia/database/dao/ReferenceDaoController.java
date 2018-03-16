package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Reference;
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
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class ReferenceDaoController extends EntityDao<Reference, Reference.ReferencePK> implements ReferenceDao {

    private static final Logger LOG = Logger.getLogger(ReferenceDaoController.class.getName());

    private static final ReferenceDaoController INSTANCE = new ReferenceDaoController();

    private ReferenceDaoController() {
        super(Reference.class);
    }

    public static ReferenceDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Reference> findAll(Long curriculumVitaeId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Reference> cq = cb.createQuery(Reference.class);
                Root<Reference> r = cq.from(Reference.class);

                if (curriculumVitaeId != null)
                    cq.where(cb.equal(
                            r.get("curriculumVitae").get("id"),
                            curriculumVitaeId
                    ));

                cq.orderBy(cb.asc(r.get("id").get("index")));

                TypedQuery<Reference> query = entityManager.createQuery(cq);
                return query.getResultList();
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
    public long count(Long curriculumVitaeId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<Reference> r = query.from(Reference.class);

                query.select(cb.count(r));

                if (curriculumVitaeId != null)
                    query.where(cb.equal(
                            r.get("curriculumVitae").get("id"),
                            curriculumVitaeId
                    ));

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
