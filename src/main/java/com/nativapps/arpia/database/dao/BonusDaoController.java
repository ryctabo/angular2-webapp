package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Bonus;
import com.nativapps.arpia.database.exception.DatabaseException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class BonusDaoController extends EntityDao<Bonus, Long>
        implements BonusDao {

    private static final BonusDaoController INSTANCE = new BonusDaoController();

    private BonusDaoController() {
        super(Bonus.class);
    }

    public static BonusDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public Bonus findByCustomerId(Long customerId, Long id) {
        return executeNamedQuery("bonus.findByCustomerId",
                new Parameter("customerId", customerId),
                new Parameter("id", id));
    }

    @Override
    public List<Bonus> findAllByCustomerIdPag(int start, int size, Long customerId) {
        return executeNamedQueryForList("bonus.findAllByCustomerId", start,
                size, new Parameter("customerId", customerId));
    }

    @Override
    public Bonus findLastBonusByCustomerId(Long customerId) {
        EntityManager manager = getEntityManager();
        try {
            TypedQuery<Bonus> query = manager.createNamedQuery("bonus.findAllBy"
                    + "CustomerIdDesc", Bonus.class).setParameter("customerId", 
                            customerId);
            List<Bonus> result = query.setMaxResults(1).getResultList();
            
            return !result.isEmpty() ? result.get(0) : null;
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public Long findAllCount(Long customerId) {
        return executeCountNamedQuery("bonus.countFindAllByCustomerId", 
                new Parameter("customerId", customerId));
    }
}
