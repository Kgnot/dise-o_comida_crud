package proj.food.vista.implementation.start;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proj.food.controller.StartViewController;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.fx.FxRuntime;
import proj.food.vista.interfaces.StartView;
import proj.food.vista.mediatr.MediatorView;

public class StartViewImplFX implements StartView {

    private StartViewController controller;
    private MediatorView mediator;
    private Stage stage;

    private StartViewController getController() {
        if (controller == null) {
            controller = new StartViewController(this);
        }
        return controller;
    }

    private void createStageIfNeeded() {
        if (stage != null) {
            return;
        }

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

        stage = new Stage();
        stage.setTitle("Start View");
        stage.setScene(new Scene(root, 430, 260));
    }

    @Override
    public void showMenu() {
        FxRuntime.runOnFxThread(() -> {
            createStageIfNeeded();
            stage.show();
            stage.toFront();
        });
    }

    @Override
    public void exit() {
        FxRuntime.runOnFxThread(() -> {
            if (stage != null) {
                stage.close();
            }
            System.exit(0);
        });
    }

    @Override
    public void goToFoodView() {
        if (stage != null) {
            stage.hide();
        }
        mediator.changeView(ViewType.FOOD);
    }

    @Override
    public void goToCustomerView() {
        if (stage != null) {
            stage.hide();
        }
        mediator.changeView(ViewType.CUSTOMER);
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}
