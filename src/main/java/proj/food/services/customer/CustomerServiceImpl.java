package proj.food.services.customer;

import proj.food.entity.CustomerEntity;
import proj.food.repository.customer.CustomerRepository;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerEntity findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void save(CustomerEntity customer) {
        customerRepository.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
