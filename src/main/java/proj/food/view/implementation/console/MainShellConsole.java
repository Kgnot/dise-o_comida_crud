package proj.food.view.implementation.console;

import proj.food.view.ViewType;
import proj.food.view.implementation.console.customer.CustomerViewConsole;
import proj.food.view.implementation.console.food.FoodViewConsole;
import proj.food.view.implementation.console.start.StartViewConsole;
import proj.food.view.interfaces.ViewApplication;
import proj.food.view.shell.Shell;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainShellConsole implements Shell {

    private final Map<ViewType, ViewApplication> views = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public static MainShellConsole create() {
        MainShellConsole shell = new MainShellConsole();
        shell.initView();
        return shell;
    }

    private void initView() {
        views.put(ViewType.START,    new StartViewConsole());
        views.put(ViewType.FOOD,     new FoodViewConsole());
        views.put(ViewType.CUSTOMER, new CustomerViewConsole());
    }

    @Override
    public void navigate(ViewType type) {
        ViewApplication view = views.get(type);
        if (view == null) throw new IllegalStateException("View not registered: " + type);
        view.show();
        if (type == ViewType.START) {
            showMenu();
        }
    }

    @Override
    public void launch(ViewType initial) {
        navigate(initial);
    }

    private void showMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Ir a Food");
            System.out.println("2. Ir a Customer");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> navigate(ViewType.FOOD);
                case "2" -> navigate(ViewType.CUSTOMER);
                case "3" -> { running = false; System.exit(0); }
                default  -> System.out.println("Opción inválida");
            }
        }
    }
}