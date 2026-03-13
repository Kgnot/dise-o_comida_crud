package proj.food.controller;

import proj.food.config.app_context.ApplicationContext;
import proj.food.services.customer.CustomerService;
import proj.food.services.dto.CustomerDto;
import proj.food.view.interfaces.CustomerView;

import java.util.List;

public class CustomerViewController {

    private final CustomerView customerView;
    private final CustomerService customerService;

    public CustomerViewController(CustomerView customerView) {
        this.customerView = customerView;
        this.customerService = ApplicationContext.getInstance().getBean(CustomerService.class);
    }

    public void processMenuOption(String option) {
        // String options keep compatibility across console, Swing, and FX views.
        switch (option) {
            case "1":
                showCustomerList();
                break;
            case "2":
                customerView.insertCustomer();
                break;
            case "3":
                customerView.updateCustomer();
                break;
            case "4":
                customerView.deleteCustomer();
                break;
            case "5":
                exit();
                break;
            default:
                customerView.showError("Invalid option");
                customerView.show();
        }
    }

    // Fetches the list of customers from the service layer and displays it in the view.
    public void showCustomerList() {
        List<CustomerDto> customerDtos = customerService.findAll();
        customerView.showCustomerList(customerDtos);
    }

    // Takes a CustomerDto from the view and attempts to save it using the service layer, then refreshes the list.
    public void insertCustomer(CustomerDto newCustomer) {
        try {
            customerService.save(newCustomer);
            showCustomerList();
        } catch (Exception e) {
            customerView.showError("Error inserting customer: " + e.getMessage());
        }
    }

    // Takes an updated CustomerDto from the view and attempts to save it using the service layer, then refreshes the list.
    public void updateCustomer(CustomerDto updatedCustomer) {
        try {
            customerService.save(updatedCustomer);
            showCustomerList();
        } catch (Exception e) {
            customerView.showError("Error updating customer: " + e.getMessage());
        }
    }

    public void deleteCustomer(CustomerDto deletedCustomer) {
        try {
            customerService.deleteById(deletedCustomer.id());
            showCustomerList();
        } catch (Exception e) {
            customerView.showError("Error deleting customer: " + e.getMessage());
        }
    }

    private void exit() {
        customerView.exit();
    }
}
