package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Schedule;
import com.nativapps.arpia.database.exception.DatabaseException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0
 */
public class ScheduleDaoController extends EntityDao<Schedule, Schedule.SchedulePK> implements ScheduleDao {

    private static final Logger LOG = Logger.getLogger(ScheduleDaoController.class.getName());

    private static final ScheduleDaoController INSTANCE = new ScheduleDaoController();

    private ScheduleDaoController() {
        super(Schedule.class);
    }

    public static ScheduleDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public void deleteByMapPointId(long mapPointId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaDelete<Schedule> query = cb.createCriteriaDelete(Schedule.class);
                Root<Schedule> s = query.from(Schedule.class);

                query.where(cb.equal(s.get("mapPoint").get("id"), mapPointId));

                entityManager.getTransaction().begin();
                entityManager.createQuery(query).executeUpdate();
                entityManager.getTransaction().commit();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
                throw new DatabaseException(ex.getCause().getMessage(), ex);
            } finally {
                entityManager.close();
            }
        }
    }
}
