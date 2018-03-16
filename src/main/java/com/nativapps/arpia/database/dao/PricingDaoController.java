package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Pricing;
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
public class PricingDaoController extends EntityDao<Pricing, Pricing.PricingPK> implements PricingDao {

    private static final Logger LOG = Logger.getLogger(PricingDaoController.class.getName());

    private static final PricingDaoController INSTANCE = new PricingDaoController();

    private PricingDaoController() {
        super(Pricing.class);
    }

    public static PricingDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public void deleteByMapPointId(long mapPointId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaDelete<Pricing> query = cb.createCriteriaDelete(Pricing.class);

                Root<Pricing> p = query.from(Pricing.class);
                query.where(cb.equal(p.get("mapPoint").get("id"), mapPointId));

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
