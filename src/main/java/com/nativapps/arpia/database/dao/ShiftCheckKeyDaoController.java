package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.ShiftCheckKey;
import com.nativapps.arpia.database.exception.DatabaseException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Luis Alfonso Lenes Salas<luislenes02@gmail.com>
 * @version 1.0
 */
public class ShiftCheckKeyDaoController extends EntityDao<ShiftCheckKey, Long >
        implements ShiftCheckKeyDao {

    private static final ShiftCheckKeyDaoController INSTANCE = new ShiftCheckKeyDaoController();

    private ShiftCheckKeyDaoController() {
        super(ShiftCheckKey.class);
    }

    public static ShiftCheckKeyDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ShiftCheckKey> findAll(Long shiftCheckId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<ShiftCheckKey> query = cb.createQuery(ShiftCheckKey.class);
            Root<ShiftCheckKey> checkKey = query.from(ShiftCheckKey.class);
            query.select(checkKey);

            if (shiftCheckId != null) {
                Path<Long> checkId = checkKey.get("check").get("id");
                query.where(cb.equal(checkId, shiftCheckId));
            }

            return manager.createQuery(query).getResultList();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public long count(Long shiftCheckId) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<ShiftCheckKey> checkKey = query.from(ShiftCheckKey.class);
            query.select(cb.count(checkKey));

            if (shiftCheckId != null) {
                Path<Long> checkId = checkKey.get("check").get("id");
                query.where(cb.equal(checkId, shiftCheckId));
            }

            return manager.createQuery(query).getSingleResult();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public ShiftCheckKey find(Long checkId, String key) {
        return executeNamedQuery("shiftCheckKey.find", new Parameter("checkId", checkId),
                new Parameter("skey", key));
    }
}
