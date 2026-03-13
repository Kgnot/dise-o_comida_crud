package proj.food.view.interfaces;

import proj.food.services.dto.CustomerDto;

import java.util.List;

// interface that defines the contract for the Customer View in the application
public interface CustomerView extends ViewApplication {

    // method to display a list of customers to the user
    void showCustomerList(List<CustomerDto> entities);

    // method to insert a new customer into the system, taking a CustomerDto as input
    void insertCustomer();

    // update an existing customer in the system, taking a CustomerDto as input
    void updateCustomer();

    // delete a customer from the system, taking the customer's ID as input
    void deleteCustomer();

    // method to display an error message to the user
    void showError(String message);
}
