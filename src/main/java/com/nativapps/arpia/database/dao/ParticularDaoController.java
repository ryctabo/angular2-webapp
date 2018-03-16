package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Gender;
import com.nativapps.arpia.database.entity.Particular;
import com.nativapps.arpia.database.entity.Phone;
import com.nativapps.arpia.database.exception.DatabaseException;
import com.nativapps.arpia.model.OrderType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class ParticularDaoController extends EntityDao<Particular, Long>
        implements ParticularDao {

    private static final Logger LOG = Logger.getLogger(ParticularDaoController.class.getName());

    private static final ParticularDaoController INSTANCE
            = new ParticularDaoController();

    private ParticularDaoController() {
        super(Particular.class);
    }

    public static ParticularDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Particular> findAll(int start, int size, String search,
            String orderBy, OrderType orderType, Gender gender) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Particular> query = cb
                    .createQuery(Particular.class);

            Root<Particular> p = query.from(Particular.class);
            query.select(p);

            Predicate[] restrictions = getRestrictions(manager, p, 
                    query, search, gender);
            if (restrictions != null)
                query.where(restrictions);

            if (orderBy != null) {
                if (orderType == null || orderType == OrderType.ASC)
                    query.orderBy(cb.asc(p.get(orderBy)));
                else
                    query.orderBy(cb.desc(p.get(orderBy)));
            }

            TypedQuery<Particular> typedQuery = manager.createQuery(query);

            return size == 0 ? typedQuery.getResultList() : typedQuery
                    .setMaxResults(size).setFirstResult(start).getResultList();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public Particular findByCustomerId(Long id) {
        return executeNamedQuery("particular.findByCustomerId",
                new Parameter("customerId", id));
    }

    @Override
    public long findAllCount(String search, Gender gender) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);

            Root<Particular> p = query.from(Particular.class);
            query.select(cb.count(p));

            Predicate[] restrictions = getRestrictions(manager, p, query, 
                    search, gender);
            if (restrictions != null)
                query.where(restrictions);

            return manager.createQuery(query).getSingleResult();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    private Predicate[] getRestrictions(EntityManager manager, Root<Particular> p, 
            CriteriaQuery query, String search, Gender gender) {

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        List<Predicate> restrictions = new ArrayList<>();
        if (search != null) {
            Root phone = query.from(Phone.class);
            Path<String> pathIdentification = p.get("identification");
            Path<String> pathName = p.get("name");
            Path<String> pathLastName = p.get("lastName");
            Path<String> pathNumber = phone.get("number");
            
            restrictions.add(cb.equal(p.get("id"), phone.get("owner").get("id")));
            restrictions.add(cb.or(cb.like(pathIdentification, "%" + search + "%"),
                    cb.like(pathName, "%" + search + "%"),
                    cb.like(pathLastName, "%" + search + "%"),
                    cb.like(pathNumber, search +"%")));
        }
        if (gender != null)
            restrictions.add(cb.equal(p.get("gender"), gender));

        return restrictions.isEmpty() ? null : restrictions
                .toArray(new Predicate[restrictions.size()]);
    }

}
