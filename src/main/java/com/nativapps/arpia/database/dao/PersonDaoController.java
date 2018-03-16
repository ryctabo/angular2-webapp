package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Person;
import com.nativapps.arpia.database.exception.DatabaseException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Gustavo Pacheco <ryctabo@gmail.com>
 * @version 1.1.0
 */
public class PersonDaoController extends EntityDao<Person, Long>
        implements PersonDao {

    private static final PersonDaoController INSTANCE = new PersonDaoController();

    private PersonDaoController() {
        super(Person.class);
    }

    public static PersonDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public Person findByIdentification(String identification) {
        return executeNamedQuery("person.findByIdentification",
                new Parameter("ide", identification));
    }

    @Override
    public List<Person> findAll(int start, int size, String search) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Person> query = cb.createQuery(Person.class);
            Root<Person> person = query.from(Person.class);

            query.select(person);

            Predicate restrictions = getRestrictions(manager, person, search);
            if (restrictions != null)
                query.where(restrictions);

            TypedQuery<Person> typedQuery = manager.createQuery(query);

            return size == 0 ? typedQuery.getResultList() : typedQuery
                    .setFirstResult(start).setMaxResults(size).getResultList();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    @Override
    public long findAllCount(String search) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<Person> person = query.from(Person.class);

            query.select(cb.count(person));

            Predicate restrictions = getRestrictions(manager, person, search);
            if (restrictions != null)
                query.where(restrictions);

            return manager.createQuery(query).getSingleResult();
        } catch (Exception ex) {
            throw new DatabaseException(ex.getCause().getMessage(), ex);
        } finally {
            if (manager != null)
                manager.close();
        }
    }

    private Predicate getRestrictions(EntityManager manager, Root<Person> person,
            String search) {
        if (search != null) {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            Path<String> name = person.get("name");
            Path<String> lastName = person.get("lastName");
            Path<String> identification = person.get("identification");

            return cb.or(cb.like(name, "%" + search + "%"),
                    cb.like(lastName, "%" + search + "%"),
                    cb.like(identification, "%" + search + "%"));
        }

        return null;
    }

}
