package proj.food.repository.abs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public abstract class BaseRepository<T, ID> {

    protected final EntityManager em;
    protected final Class<T> entityClass;


    protected BaseRepository(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public List<T> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(entityClass);
        var root = cq.from(entityClass);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

    public T findById(ID id) {
        return em.find(entityClass, id);
    }

    public void save(T entity) {
        try {
            em.getTransaction().begin();
            ID id = extractId(entity);
            // Null id means a new entity; otherwise JPA merges detached state.
            if (id == null) {
                em.persist(entity);
            } else {
                em.merge(entity);
            }

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                // Roll back partial work to keep repository operations atomic.
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public void deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            try {
                em.getTransaction().begin();
                em.remove(entity);
                em.getTransaction().commit();
            } catch (RuntimeException e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                throw e;
            }
        }
    }

    protected abstract ID extractId(T entity);


}
