package proj.food.repository.customer;

import proj.food.entity.CustomerEntity;

import java.util.List;

public interface CustomerRepository {

    List<CustomerEntity> findAll();

    CustomerEntity findById(Long id);

    void save(CustomerEntity customer);

    void deleteById(Long id);

}
