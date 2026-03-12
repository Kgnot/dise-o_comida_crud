package proj.food.vista.interfaces;

import proj.food.services.dto.CustomerDto;

import java.util.List;

public interface CustomerView extends ViewApplication {

    void showCustomerList(List<CustomerDto> entities);

    void showError(String message);
}
