package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.*;
import com.nativapps.arpia.database.exception.DatabaseException;
import com.nativapps.arpia.model.OrderType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 2.0
 */
public class InventoryDaoController extends EntityDao<Inventory, Long> implements InventoryDao {

    private static final Logger LOG = Logger.getLogger(InventoryDaoController.class.getName());

    private static final InventoryDaoController INSTANCE = new InventoryDaoController();

    private InventoryDaoController() {
        super(Inventory.class);
    }

    public static InventoryDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public Inventory findByName(String name) {
        return executeNamedQuery("inventory.findByName", new Parameter("name", name));
    }

    @Override
    public List<Inventory> findAll(String search, String orderBy, OrderType type,
                                   int start, int size) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Inventory> cq = cb.createQuery(Inventory.class);
                Root<Inventory> i = cq.from(Inventory.class);

                if (search != null) {
                    Path<String> nameProperty = i.get("name");
                    cq.where(cb.like(nameProperty, "%" + search + "%"));
                }

                Path propOrderBy = i.get("id");
                if (orderBy != null)
                    propOrderBy = i.get(orderBy);

                Order order;
                if (type == null || type == OrderType.ASC)
                    order = cb.asc(propOrderBy);
                else
                    order = cb.desc(propOrderBy);
                cq.orderBy(order);

                TypedQuery<Inventory> query = entityManager.createQuery(cq);

                return query.setMaxResults(size)
                        .setFirstResult(start)
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
    public long count(String search) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<Inventory> i = query.from(Inventory.class);

                query.select(cb.count(i));

                if (search != null) {
                    Path<String> nameProperty = i.get("name");
                    query.where(cb.like(nameProperty, "%" + search + "%"));
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
    public List<MessengerInfo> getMessengers(long inventoryId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                final String SQL = "SELECT SUM(ci.count), c.messenger "
                        + "FROM ChipItem ci JOIN ci.chip c "
                        + "WHERE ci.element.id = :id AND c.type = :ctype "
                        + "GROUP BY c.messenger.id";
                TypedQuery<Object[]> query = entityManager.createQuery(SQL, Object[].class);
                query.setParameter("id", inventoryId);

                query.setParameter("ctype", ChipType.LOAN);
                List<Object[]> loanResult = query.getResultList();

                List<MessengerInfo> messengers = new ArrayList<>();
                for (Object[] objects : loanResult) {
                    MessengerInfo m = new MessengerInfo();
                    m.setLoans(Integer.parseInt(objects[0].toString()));
                    m.setMessenger((Messenger) objects[1]);
                    messengers.add(m);
                }

                query.setParameter("ctype", ChipType.RETURN);
                List<Object[]> returnResult = query.getResultList();

                for (Object[] objects : returnResult) {
                    MessengerInfo m = null;
                    for (MessengerInfo me : messengers) {
                        if (((Messenger) objects[1]).getId() == me.getMessenger().getId()) {
                            m = me;
                        }
                    }
                    if (m == null) {
                        m = new MessengerInfo();
                        m.setMessenger((Messenger) objects[1]);
                    }
                    m.setReturns(Integer.parseInt(objects[0].toString()));
                }

                return messengers;
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
    public ChipInfo getChipInfo(long inventoryId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                final String SQL = "SELECT SUM(ci.count) " +
                        "FROM ChipItem ci JOIN ci.chip c " +
                        "WHERE c.type = :ctype AND ci.element.id = :id";
                Query query = entityManager.createQuery(SQL);
                query.setParameter("id", inventoryId);
                Object result = null;

                query.setParameter("ctype", ChipType.LOAN);
                result = query.getSingleResult();
                int loans = result == null ? 0 : Integer.parseInt(result.toString());

                query.setParameter("ctype", ChipType.RETURN);
                result = query.getSingleResult();
                int returns = result == null ? 0 : Integer.parseInt(result.toString());

                return new ChipInfo(loans, returns);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
                throw new DatabaseException(ex.getCause().getMessage(), ex);
            } finally {
                entityManager.close();
            }
        }
        return null;
    }

}
