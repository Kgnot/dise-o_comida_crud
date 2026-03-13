package proj.food.vista.implementation.console.food;

import proj.food.controller.FoodViewController;
import proj.food.services.dto.FoodDto;
import proj.food.vista.ViewType;
import proj.food.vista.interfaces.FoodView;
import proj.food.vista.mediatr.MediatorView;

import java.util.List;
import java.util.Scanner;

public class FoodViewConsole implements FoodView {

    private final Scanner scanner = new Scanner(System.in);
    private FoodViewController controller;
    private MediatorView mediator;

    private FoodViewController getController() {
        if (controller == null) {
            controller = new FoodViewController(this);
        }
        return controller;
    }

    @Override
    public void showMenu() {
        System.out.println("\n=== FOOD MENU ===");
        System.out.println("1. Show Food List");
        System.out.println("2. Insert New Food");
        System.out.println("3. Update Existing Food");
        System.out.println("4. Delete Food");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");

        String option = scanner.nextLine();
        getController().processMenuOption(option);
    }

    @Override
    public void showFoodList(List<FoodDto> dtos) {
        System.out.println("\n=== FOOD LIST ===");
        if (dtos.isEmpty()) {
            System.out.println("No foods found.");
        } else {
            for (FoodDto dto : dtos) {
                System.out.println("ID: " + dto.id() + ", Name: " + dto.name() + ", Price: " + dto.price());
            }
        }
    }

    @Override
    public void insertFood() {
        System.out.println("Para insertar comida necesitamos nombre y precio:");
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        Double price = readPrice();
        if (price == null) {
            return;
        }

        FoodDto newFood = new FoodDto(null, name, price);
        getController().insertFood(newFood);
    }

    @Override
    public void updateFood() {
        System.out.println("Para actualizar comida necesitamos ID, nombre y precio:");
        Long id = readId();
        if (id == null) {
            return;
        }

        System.out.print("Nuevo Nombre: ");
        String newName = scanner.nextLine();
        Double newPrice = readPrice();
        if (newPrice == null) {
            return;
        }

        FoodDto updatedFood = new FoodDto(id, newName, newPrice);
        getController().updateFood(updatedFood);
    }

    @Override
    public void deleteFood() {
        System.out.println("Para eliminar comida necesitamos su ID:");
        Long id = readId();
        if (id == null) {
            return;
        }

        FoodDto deletedFood = new FoodDto(id, null, null);
        getController().deleteFood(deletedFood);
    }

    private Long readId() {
        System.out.print("ID: ");
        String idStr = scanner.nextLine();
        try {
            return Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            showError("El ID debe ser un número");
            return null;
        }
    }

    private Double readPrice() {
        System.out.print("Precio: ");
        String priceStr = scanner.nextLine();
        try {
            return Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            showError("El precio debe ser un número");
            return null;
        }
    }

    @Override
    public void showError(String message) {
        System.err.println("ERROR: " + message);
    }

    @Override
    public void exit() {
        System.out.println("Saliendo de la Vista de Comida...");
        scanner.close();
        if (mediator != null) {
            mediator.changeView(ViewType.START);
        }
    }

    @Override
    public void setMediator(MediatorView mediator) {
        this.mediator = mediator;
    }
}
