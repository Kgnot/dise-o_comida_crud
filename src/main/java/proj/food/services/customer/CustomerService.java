package proj.food.services.customer;

import proj.food.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {

    List<CustomerEntity> findAll();

    CustomerEntity findById(Long id);

    void save(CustomerEntity customer);

    void deleteById(Long id);

}
