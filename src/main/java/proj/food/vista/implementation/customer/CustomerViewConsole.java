package proj.food.vista.implementation.customer;

import proj.food.controller.CustomerViewController;
import proj.food.services.dto.CustomerDto;
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
        System.out.println("2. Exit");
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
    public void showError(String message) {
        System.err.println("ERROR: " + message);
    }

    @Override
    public void exit() {
        System.out.println("Exiting Customer View...");
        scanner.close();
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}
