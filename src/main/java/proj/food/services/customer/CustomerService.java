package proj.food.services.customer;

import proj.food.services.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> findAll();

    CustomerDto findById(Long id);

    void save(CustomerDto customer);

    void deleteById(Long id);
}
