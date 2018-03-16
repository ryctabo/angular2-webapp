package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Neighborhood;
import com.nativapps.arpia.database.exception.DatabaseException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
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
public class NeighborhoodDaoController extends EntityDao<Neighborhood, Long> implements NeighborhoodDao {

    private static final Logger LOG = Logger.getLogger(NeighborhoodDaoController.class.getName());

    private static final NeighborhoodDaoController INSTANCE = new NeighborhoodDaoController();

    private NeighborhoodDaoController() {
        super(Neighborhood.class);
    }

    public static NeighborhoodDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Neighborhood> findAll(int start, int size, String search) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Neighborhood> cq = cb.createQuery(Neighborhood.class);
                Root<Neighborhood> neighborhood = cq.from(Neighborhood.class);

                if (search != null) {
                    Path<String> name = neighborhood.get("name");
                    cq.where(cb.like(name, "%" + search + "%"));
                }

                TypedQuery<Neighborhood> query = entityManager.createQuery(cq);

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
    public long findAllCount(String search) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<Neighborhood> neighborhood = query.from(Neighborhood.class);
                query.select(cb.count(neighborhood));

                if (search != null) {
                    Path<String> name = neighborhood.get("name");
                    query.where(cb.like(name, "%" + search + "%"));
                }

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

    @Override
    public Neighborhood findByName(String name) {
        return executeNamedQuery("neighborhood.findByName",
                new Parameter("name", name));
    }
}
