package proj.food.controller;

import proj.food.entity.CustomerEntity;
import proj.food.vista.interfaces.CustomerView;

import java.util.List;

public class CustomerViewController {

    private final CustomerView customerView;


    public CustomerViewController(CustomerView customerView) {
        this.customerView = customerView;
    }

    public void processMenuOption(String option) {
        switch (option) {
            case "1":
                showCustomerList();
                break;
            case "2":
                exit();
                break;
            default:
                customerView.showError("Invalid option");
                customerView.showMenu();
        }
    }

    public void showCustomerList() {
        List<CustomerEntity> customerEntities = List.of(
                new CustomerEntity(1L, "John Doe"),
                new CustomerEntity(2L, "Jane Smith"));
        customerView.showCustomerList(customerEntities);
    }

    private void exit() {
        customerView.exit();
    }
}


