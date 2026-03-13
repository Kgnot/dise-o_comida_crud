package proj.food.vista.implementation.console.customer;

import proj.food.controller.CustomerViewController;
import proj.food.services.dto.CustomerDto;
import proj.food.vista.ViewType;
import proj.food.vista.interfaces.CustomerView;
import proj.food.vista.mediatr.MediatorView;

import java.util.List;
import java.util.Scanner;
// Implementation of the CustomerView interface for console-based interaction
public class CustomerViewConsole implements CustomerView {

    private CustomerViewController controller;
    private final Scanner scanner = new Scanner(System.in);
    private MediatorView mediator; // Reference to the mediator for view navigation

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
    // Display the list of customers in the console
    @Override
    public void showCustomerList(List<CustomerDto> dtos) {
        System.out.println("\n=== LISTA DE CLIENTES ===");
        if (dtos.isEmpty()) {
            System.out.println("No se encontraron clientes.");
        } else {
            for (CustomerDto dto : dtos) {
                System.out.println("ID: " + dto.id() + ", Nombre: " + dto.name());
            }
        }
    }
    // Prompt the user for customer details and insert a new customer
    @Override
    public void insertCustomer() {
        System.out.println("Para insertar un cliente necesitamos su nombre:");
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        CustomerDto newCustomer = new CustomerDto(null, name);
        getController().insertCustomer(newCustomer);
    }
    // Prompt the user for customer ID and new name to update an existing customer
    @Override
    public void updateCustomer() {
        System.out.println("Para actualizar un cliente necesitamos su ID y su nuevo nombre:");
        System.out.print("ID: ");
        String idStr = scanner.nextLine();
        Long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            showError("El ID debe ser un número");
            return;
        }
        System.out.print("Nuevo Nombre: ");
        String newName = scanner.nextLine();
        CustomerDto updatedCustomer = new CustomerDto(id, newName);
        getController().updateCustomer(updatedCustomer);
    }
    // Prompt the user for customer ID to delete a customer
    @Override
    public void deleteCustomer() {
        System.out.println("Para eliminar un cliente necesitamos su ID:");
        System.out.print("ID: ");
        String idStr = scanner.nextLine();
        Long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            showError("El ID debe ser un número");
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
        System.out.println("Saliendo de la Vista de Clientes...");
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
