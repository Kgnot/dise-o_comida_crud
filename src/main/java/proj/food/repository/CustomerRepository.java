package proj.food.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import proj.food.entity.CustomerEntity;

import java.util.List;

// esde debe implementar un repositorio
public class CustomerRepository {

    private EntityManager em;

    public CustomerRepository(EntityManager em) {
        this.em = em;
    }

    public void save(CustomerEntity customer) {
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
    }

    // aqui deberia devolver el modelo, no la entidad
    public List<CustomerEntity> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(CustomerEntity.class);
        var root = cq.from(CustomerEntity.class);
        cq.select(root);

        return em.createQuery(cq).getResultList();
    }

}
