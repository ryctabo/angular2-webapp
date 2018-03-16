package com.nativapps.arpia.database.dao;

import com.nativapps.arpia.database.EntityDao;
import com.nativapps.arpia.database.entity.Document;
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
 * @author Luis Alfonso Lenes Salas <luislenes02@gmail.com>
 * @version 1.0
 */
public class DocumentDaoController extends EntityDao<Document, Long>
        implements DocumentDao {

    private static final DocumentDaoController INSTANCE
            = new DocumentDaoController();

    private DocumentDaoController() {
        super(Document.class);
    }

    public static DocumentDaoController getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Document> findAll(int start, int size, String search) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Document> query = cb.createQuery(Document.class);

            Root<Document> document = query.from(Document.class);
            query.select(document);

            Predicate restrictions = getRestrictions(manager, document, search);
            if (restrictions != null)
                query.where(restrictions);

            TypedQuery<Document> typedQuery = manager.createQuery(query);

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
    public Document findByName(String name) {
        return executeNamedQuery("document.findByName", new Parameter("name",
                name));
    }

    @Override
    public Long findAllCount(String search) {
        EntityManager manager = getEntityManager();
        try {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);

            Root<Document> document = query.from(Document.class);
            query.select(cb.count(document));

            Predicate restrictions = getRestrictions(manager, document, search);
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

    private Predicate getRestrictions(EntityManager manager,
            Root<Document> document, String search) {

        if (search != null) {
            CriteriaBuilder cb = manager.getCriteriaBuilder();
            Path<String> name = document.get("name");
            return cb.like(name, "%" + search + "%");
        }

        return null;
    }

    @Override
    public Document findByUrl(String url) {
        return executeNamedQuery("document.findByUrl", new Parameter("url", url));
    }
}
