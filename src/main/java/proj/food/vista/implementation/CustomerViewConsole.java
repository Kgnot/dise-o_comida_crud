// Vista - SOLO muestra y captura entrada
package proj.food.vista.implementation;

import proj.food.controller.CustomerViewController;
import proj.food.entity.CustomerEntity;
import proj.food.vista.interfaces.CustomerView;

import java.util.List;
import java.util.Scanner;

public class CustomerViewConsole implements CustomerView {

    private CustomerViewController controller;
    private Scanner scanner;

    public CustomerViewConsole() {
        this.scanner = new Scanner(System.in);
        this.controller = getController(); // Inicializar controller
    }

    @Override
    public void showMenu() {
        System.out.println("\n=== CUSTOMER MENU ===");
        System.out.println("1. Show Customer List");
        System.out.println("2. Exit");
        System.out.print("Choose an option: ");

        String option = scanner.nextLine();
        controller.processMenuOption(option);
    }

    private CustomerViewController getController() {
        if (controller == null) {
            controller = new CustomerViewController(this);
        }
        return controller;
    }

    @Override
    public void showCustomerList(List<CustomerEntity> entities) {
        System.out.println("\n=== CUSTOMER LIST ===");
        if (entities.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            for (CustomerEntity entity : entities) {
                System.out.println("ID: " + entity.getId() + ", Name: " + entity.getName());
            }
        }
    }

    @Override
    public void showError(String message) {
        System.err.println("ERROR: " + message);
    }

    @Override
    public void exit() {
        System.out.println("Exiting Customer View...");
        scanner.close();
    }
}
