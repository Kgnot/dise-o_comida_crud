package proj.food.view.implementation.console.start;

import proj.food.view.interfaces.StartView;

import java.util.Scanner;

public class StartViewConsole implements StartView {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void show() {
        System.out.println("¡Bienvenidos a la plataforma de comida!");
    }

    @Override
    public void exit() {
        System.exit(0);
    }


}