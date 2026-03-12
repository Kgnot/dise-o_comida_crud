package proj.food.controller;

import proj.food.config.app_context.ApplicationContext;
import proj.food.services.customer.CustomerService;
import proj.food.services.dto.CustomerDto;
import proj.food.vista.interfaces.CustomerView;

import java.util.List;

public class CustomerViewController {

    private final CustomerView customerView;
    private final CustomerService customerService;

    public CustomerViewController(CustomerView customerView) {
        this.customerView = customerView;
        this.customerService = ApplicationContext.getInstance().getBean(CustomerService.class);
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
        List<CustomerDto> customerDtos = customerService.findAll();
        customerView.showCustomerList(customerDtos);
    }

    private void exit() {
        customerView.exit();
    }
}
