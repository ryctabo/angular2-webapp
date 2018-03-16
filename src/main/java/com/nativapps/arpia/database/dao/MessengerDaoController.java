package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.ChipType;
import com.nativapps.arpia.database.entity.Inventory;
import com.nativapps.arpia.database.entity.InventoryInfo;
import com.nativapps.arpia.database.entity.Messenger;
import com.nativapps.arpia.database.exception.DatabaseException;
import com.nativapps.arpia.model.OrderType;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Cristobal Romero Rossi <cristobal@nativapps.mobi>
 * @author Gustavo Paheco <ryctabo@gmail.com>
 * @version 1.2.1
 */
public class MessengerDaoController extends EntityDao<Messenger, Long> implements MessengerDao {

    private static final Logger LOG = Logger.getLogger(MessengerDaoController.class.getName());

    private static final MessengerDaoController INSTANCE = new MessengerDaoController();

    private MessengerDaoController() {
        super(Messenger.class);
    }

    public static MessengerDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Messenger> findAll(int start, int size) {
        return executeNamedQueryForList("messenger.findAll", start, size);
    }

    @Override
    public List<Messenger> findAll(Boolean dismissal, Integer category, String search,
            int start, int size, String orderBy, OrderType orderType,
            Boolean retire) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Messenger> cq = cb.createQuery(Messenger.class);
                Root<Messenger> m = cq.from(Messenger.class);

                Predicate[] restrictions = getRestrictions(entityManager, m, dismissal, category, search, retire);
                if (restrictions != null) {
                    cq.where(restrictions);
                }

                Path propOrderBy = m.get("id");
                if (orderBy != null) {
                    propOrderBy = m.get(orderBy);
                }

                Order order = orderType == null || orderType == OrderType.DESC
                        ? cb.desc(propOrderBy) : cb.asc(propOrderBy);
                cq.orderBy(order);

                TypedQuery<Messenger> query = entityManager.createQuery(cq);

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
    public long getCount(Boolean dismissal, Integer category, String search, Boolean retire) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<Long> query = cb.createQuery(Long.class);
                Root<Messenger> m = query.from(Messenger.class);
                query.select(cb.count(m));
                
                Predicate[] restrictions = getRestrictions(entityManager, m, dismissal, category, search, retire);
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

    private Predicate[] getRestrictions(EntityManager manager, Root<Messenger> messenger,
            Boolean dismissal, Integer category, String search, Boolean retire) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();

        Set<Predicate> restrictions = new HashSet<>();
        if (search != null) {
            Path<String> pa1 = messenger.get("identification");
            Path<String> pa2 = messenger.get("name");
            Path<String> pa3 = messenger.get("lastName");

            Predicate p1 = cb.like(pa1, "%" + search + "%");
            Predicate p2 = cb.like(pa2, "%" + search + "%");
            Predicate p3 = cb.like(pa3, "%" + search + "%");

            restrictions.add(cb.or(p1, p2, p3));
        }
        if (dismissal != null) {
            restrictions.add(cb.equal(messenger.get("dismissal"), dismissal));
        }
        if (category != null) {
            restrictions.add(cb.equal(messenger.get("category"), category));
        }

        if (retire != null) {
            restrictions.add(cb.equal(messenger.get("retire"), retire));
        }
        
        return !restrictions.isEmpty() ? restrictions.toArray(new Predicate[restrictions.size()]) : null;
    }

    @Override
    public List<InventoryInfo> getInventoryInfo(long messengerId) {
        EntityManager entityManager = getEntityManager();
        if (entityManager != null) {
            try {
                final String LOAN_SQL = "SELECT ci.element, SUM(ci.count) "
                        + "FROM ChipItem ci JOIN ci.chip c "
                        + "WHERE c.messenger.id = :id AND c.type = :ctype "
                        + "GROUP BY ci.element.id";
                TypedQuery<Object[]> query = entityManager.createQuery(LOAN_SQL, Object[].class);
                query.setParameter("id", messengerId);

                query.setParameter("ctype", ChipType.LOAN);
                List<Object[]> loanResult = query.getResultList();

                List<InventoryInfo> response = new ArrayList<>();
                for (Object[] objects : loanResult) {
                    InventoryInfo inventory = new InventoryInfo();
                    inventory.setInventory((Inventory) objects[0]);
                    inventory.setLoans(Integer.parseInt(objects[1].toString()));

                    response.add(inventory);
                }

                query.setParameter("ctype", ChipType.RETURN);
                List<Object[]> returnResult = query.getResultList();
                for (Object[] objs : returnResult) {
                    InventoryInfo inventory = null;
                    for (InventoryInfo i : response) {
                        if (i.getInventory().getId() == ((Inventory) objs[0]).getId()) {
                            inventory = i;
                        }
                    }
                    if (inventory == null) {
                        inventory = new InventoryInfo();
                        inventory.setInventory((Inventory) objs[0]);
                    }
                    inventory.setReturns(Integer.parseInt(objs[1].toString()));
                }

                return response;
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getCause().getMessage(), ex);
                throw new DatabaseException(ex.getCause().getMessage(), ex);
            } finally {
                entityManager.close();
            }
        }
        return Collections.emptyList();
    }

}
