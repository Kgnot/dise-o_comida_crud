package proj.food.vista.implementation.console.customer;

import proj.food.controller.CustomerViewController;
import proj.food.services.dto.CustomerDto;
import proj.food.vista.ViewType;
import proj.food.vista.interfaces.CustomerView;
import proj.food.vista.mediatr.MediatorView;

import java.util.List;
import java.util.Scanner;

public class CustomerViewConsole implements CustomerView {

    private CustomerViewController controller;
    private Scanner scanner = new Scanner(System.in);
    private MediatorView mediator;

    public CustomerViewConsole() {
    }

    @Override
    public void showMenu() {
        System.out.println("\n=== CUSTOMER MENU ===");
        System.out.println("1. Show Customer List");
        System.out.println("2. Insert New Customer");
        System.out.println("3. Update Existing Customer");
        System.out.println("4. Delete Customer");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");

        String option = scanner.nextLine();
        getController().processMenuOption(option);
    }

    private CustomerViewController getController() {
        if (controller == null) {
            controller = new CustomerViewController(this);
        }
        return controller;
    }

    @Override
    public void showCustomerList(List<CustomerDto> dtos) {
        System.out.println("\n=== CUSTOMER LIST ===");
        if (dtos.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (CustomerDto dto : dtos) {
                System.out.println("ID: " + dto.id() + ", Name: " + dto.name());
            }
        }
    }

    @Override
    public void insertCustomer() {
        System.out.println("Para insertar un usuario necesitamos su nombre:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        CustomerDto newCustomer = new CustomerDto(null, name);
        getController().insertCustomer(newCustomer);
    }

    @Override
    public void updateCustomer() {
        System.out.println("Para actualizar un usuario necesitamos su ID y su nuevo nombre:");
        System.out.print("ID: ");
        String idStr = scanner.nextLine();
        Long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            showError("ID must be a number");
            return;
        }
        System.out.print("New Name: ");
        String newName = scanner.nextLine();
        CustomerDto updatedCustomer = new CustomerDto(id, newName);
        getController().updateCustomer(updatedCustomer);
    }

    @Override
    public void deleteCustomer() {
        System.out.println("Para eliminar un usuario necesitamos su ID:");
        System.out.print("ID: ");
        String idStr = scanner.nextLine();
        Long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            showError("ID must be a number");
            return;
        }
        CustomerDto deletedCustomer = new CustomerDto(id, null);
        getController().deleteCustomer(deletedCustomer);
    }

    @Override
    public void showError(String message) {
        System.err.println("ERROR: " + message);
    }

    @Override
    public void exit() {
        System.out.println("Exiting Customer View...");
        scanner.close();
        if (mediator != null) {
            mediator.changeView(ViewType.START);
        }
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}
