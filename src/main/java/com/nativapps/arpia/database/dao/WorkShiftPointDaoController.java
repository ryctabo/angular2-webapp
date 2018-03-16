package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.WorkShiftPoint;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class WorkShiftPointDaoController extends EntityDao<WorkShiftPoint, Long> implements WorkShiftPointDao {

    private static final WorkShiftPointDaoController INSTANCE = new WorkShiftPointDaoController();

    private WorkShiftPointDaoController() {
        super(WorkShiftPoint.class);
    }

    public static WorkShiftPointDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<WorkShiftPoint> findAll(Long mapPointId, int start, int size) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<WorkShiftPoint> cq = cb.createQuery(WorkShiftPoint.class);
            Root<WorkShiftPoint> wsp = cq.from(WorkShiftPoint.class);

            if (mapPointId != null && mapPointId > 0)
                cq.where(cb.equal(wsp.get("mapPoint").get("id"), mapPointId));

            TypedQuery<WorkShiftPoint> query = entityManager.createQuery(cq);
            return query.setFirstResult(start)
                    .setMaxResults(size)
                    .getResultList();
        }
        return Collections.emptyList();
    }

    @Override
    public long count(Long mapPointId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<WorkShiftPoint> wsp = query.from(WorkShiftPoint.class);

            query.select(cb.count(wsp));

            if (mapPointId != null && mapPointId > 0)
                query.where(cb.equal(wsp.get("mapPoint").get("id"), mapPointId));

            return entityManager.createQuery(query).getSingleResult();
        }
        return -1L;
    }
}
