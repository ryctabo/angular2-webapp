package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.*;
import com.nativapps.arpia.database.exception.DatabaseException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class CustomerDataDaoController extends EntityDao<CustomerData, Long> implements CustomerDataDao {

    private static final Logger LOG = Logger.getLogger(CustomerDataDaoController.class.getName());

    private static final CustomerDataDaoController INSTANCE = new CustomerDataDaoController();

    private CustomerDataDaoController() {
        super(CustomerData.class);
    }

    public static CustomerDataDaoController getInstance() {
        return INSTANCE;
    }

    /**
     * Get predicate from the given parameter.
     *
     * @param cb     criteria builder
     * @param c      customer structure
     * @param search value to search
     * @return null, if the {@code search} param is null.
     */
    private Predicate getPredicate(CriteriaBuilder cb, Root<CustomerData> c, String search) {
        if (search != null) {
            Join<CustomerData, Particular> p = c.join("particular", JoinType.LEFT);
            Join<Particular, Phone> phone = p.join("phones", JoinType.LEFT);
            Join<CustomerData, Establishment> e = c.join("establishment", JoinType.LEFT);

            Path<String> name = p.get("name");
            Path<String> lastName = p.get("lastName");
            Path<String> ide = p.get("identification");

            Path<String> fullName = e.get("name");
            Path<String> nit = e.get("nit");

            Path<String> number = phone.get("number");

            String valueToSearch = "%" + search + "%";
            Predicate p1 = cb.like(cb.concat(name, cb.concat(" ", lastName)), valueToSearch);
            Predicate p2 = cb.like(ide, valueToSearch);
            Predicate p3 = cb.like(fullName, valueToSearch);
            Predicate p4 = cb.like(nit, valueToSearch);
            Predicate p5 = cb.like(number, valueToSearch);

            return cb.or(p1, p2, p3, p4, p5);
        }
        return null;
    }

    @Override
    public List<CustomerData> findAllPaginated(int start, int size, String search) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<CustomerData> cq = cb.createQuery(CustomerData.class);
                Root<CustomerData> customer = cq.from(CustomerData.class);

                Predicate restrictions = getPredicate(cb, customer, search);
                if (restrictions != null)
                    cq.where(restrictions);

                TypedQuery<CustomerData> query = entityManager.createQuery(cq);
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
    public Long findAllCount(String search) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<CustomerData> customer = query.from(CustomerData.class);

                query.select(cb.count(customer));

                Predicate restrictions = getPredicate(cb, customer, search);
                if (restrictions != null)
                    query.where(restrictions);

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
    public Particular findParticular(Long customerId) {
        EntityManager manager = getEntityManager();
        try {
            Query query = manager.createNamedQuery("customerData.findParticular")
                    .setParameter("id", customerId);

            Object[] result = (Object[]) query.getSingleResult();

            return new Particular(result[0].toString(), result[1].toString(),
                    result[2].toString());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public Establishment findEstablishment(Long customerId) {
        EntityManager manager = getEntityManager();
        try {
            Query query = manager.createNamedQuery("customerData.findEstablishment")
                    .setParameter("id", customerId);

            Object[] result = (Object[]) query.getSingleResult();

            return new Establishment(result[0].toString(), result[1].toString());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public List<CustomerData> findByNotCommunication(int start, int size, Calendar date) {
        return executeNamedQueryForList("customerData.noComunication", start,
                size, new Parameter("date", date));
    }

    @Override
    public long countByNotCommunication(Calendar date) {
        return executeCountNamedQuery("customerData.noComunicationCount",
                new Parameter("date", date));
    }

    @Override
    public List<CustomerData> findByNeighborhood(long neighborhood, int start, int size) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<CustomerData> cq = cb.createQuery(CustomerData.class);
                Root<CustomerData> c = cq.from(CustomerData.class);

                cq.select(c);

                Join<CustomerData, Particular> p = c.join("particular", JoinType.LEFT);
                Join<CustomerData, Establishment> e = c.join("establishment", JoinType.LEFT);
                Root<Address> a = cq.from(Address.class);

                Predicate pvsa = cb.equal(a.get("person").get("id"), p.get("id"));
                Predicate evsa = cb.equal(a.get("establishment").get("id"), e.get("id"));

                cq.where(cb.or(pvsa, evsa), cb.equal(a.get("neighborhood").get("id"), neighborhood));

                TypedQuery<CustomerData> query = entityManager.createQuery(cq);
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
    public long countByNeighborhood(long neighborhood) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<CustomerData> c = query.from(CustomerData.class);

                query.select(cb.count(c));

                Join<CustomerData, Particular> p = c.join("particular", JoinType.LEFT);
                Join<CustomerData, Establishment> e = c.join("establishment", JoinType.LEFT);
                Root<Address> a = query.from(Address.class);

                Predicate pvsa = cb.equal(a.get("person").get("id"), p.get("id"));
                Predicate evsa = cb.equal(a.get("establishment").get("id"), e.get("id"));

                query.where(cb.or(pvsa, evsa), cb.equal(a.get("neighborhood").get("id"), neighborhood));

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
