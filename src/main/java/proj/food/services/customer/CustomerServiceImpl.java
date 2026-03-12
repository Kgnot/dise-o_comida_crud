package proj.food.services.customer;

import proj.food.repository.customer.CustomerRepository;
import proj.food.services.dto.CustomerDto;
import proj.food.services.mapper.CustomerMapper;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDto> findAll() {
        return CustomerMapper.toDtoList(customerRepository.findAll());
    }

    @Override
    public CustomerDto findById(Long id) {
        return CustomerMapper.toDto(customerRepository.findById(id));
    }

    @Override
    public void save(CustomerDto customer) {
        customerRepository.save(CustomerMapper.toEntity(customer));
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
