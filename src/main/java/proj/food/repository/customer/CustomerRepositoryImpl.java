package proj.food.repository.customer;

import jakarta.persistence.EntityManager;
import proj.food.entity.CustomerEntity;
import proj.food.repository.abs.BaseRepository;


public class CustomerRepositoryImpl extends BaseRepository<CustomerEntity, Long>
        implements CustomerRepository {

    public CustomerRepositoryImpl(EntityManager em) {
        super(em, CustomerEntity.class);
    }

    @Override
    protected Long extractId(CustomerEntity entity) {
        return entity.getId();
    }
}
