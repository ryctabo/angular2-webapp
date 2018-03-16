package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Particular;
import com.nativapps.arpia.database.exception.DatabaseException;
import com.nativapps.arpia.model.adapter.BirthdayPeriod;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Victor Del Rio Prens <vprens12@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.0.1-SNAPSHOT
 */
public class BirthdayDaoController extends EntityDao<Particular, Long> implements BirthdayDao {

    private static final Logger LOG = Logger.getLogger(BirthdayDaoController.class.getName());

    private static final BirthdayDaoController INSTANCE = new BirthdayDaoController();

    private BirthdayDaoController() {
        super(Particular.class);
    }

    public static BirthdayDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Particular> findAll(int start, int size, BirthdayPeriod period) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Particular> cq = cb.createQuery(Particular.class);
                Root<Particular> p = cq.from(Particular.class);

                Path<Calendar> birthday = p.get("birthday");
                cq.where(cb.between(
                        cb.function("DAYOFYEAR", Integer.class, birthday),
                        period.getFrom().get(Calendar.DAY_OF_YEAR),
                        period.getTo().get(Calendar.DAY_OF_YEAR)
                ));

                TypedQuery<Particular> query = entityManager.createQuery(cq);
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
    public long count(BirthdayPeriod period) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<Particular> p = query.from(Particular.class);

                query.select(cb.count(p));

                Path<Calendar> birthday = p.get("birthday");
                query.where(cb.between(
                        cb.function("DAYOFYEAR", Integer.class, birthday),
                        period.getFrom().get(Calendar.DAY_OF_YEAR),
                        period.getTo().get(Calendar.DAY_OF_YEAR)
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
