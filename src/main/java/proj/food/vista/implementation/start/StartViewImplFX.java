package proj.food.vista.implementation.start;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proj.food.controller.StartViewController;
import proj.food.vista.ViewType;
import proj.food.vista.interfaces.StartView;
import proj.food.vista.mediatr.MediatorView;

public class StartViewImplFX extends Application implements StartView {

    private StartViewController controller;
    private MediatorView mediator;
    private Stage stage;

    private StartViewController getController() {
        if (controller == null) {
            controller = new StartViewController(this);
        }
        return controller;
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        Label header = new Label("Bienvenidos a la plataforma de comida!");
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Button btnFood = new Button("Ir a Food View");
        Button btnCustomer = new Button("Ir a Customer View");
        Button btnExit = new Button("Exit");

        btnFood.setOnAction(e -> getController().processMenuOption("1"));
        btnCustomer.setOnAction(e -> getController().processMenuOption("2"));
        btnExit.setOnAction(e -> getController().processMenuOption("3"));

        VBox root = new VBox(12, header, btnFood, btnCustomer, btnExit);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(16));

        primaryStage.setTitle("Start View");
        primaryStage.setScene(new Scene(root, 430, 260));
        primaryStage.show();
    }

    @Override
    public void showMenu() {
        Application.launch(StartViewImplFX.class);
    }

    @Override
    public void exit() {
        if (stage != null) {
            stage.close();
        }
        System.exit(0);
    }

    @Override
    public void goToFoodView() {
        mediator.changeView(ViewType.FOOD);
    }

    @Override
    public void goToCustomerView() {
        mediator.changeView(ViewType.CUSTOMER);
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}

