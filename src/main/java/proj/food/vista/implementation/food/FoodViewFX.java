package proj.food.vista.implementation.food;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proj.food.controller.FoodViewController;
import proj.food.services.dto.FoodDto;
import proj.food.vista.implementation.fx.FxRuntime;
import proj.food.vista.interfaces.FoodView;
import proj.food.vista.mediatr.MediatorView;

import java.util.List;

public class FoodViewFX implements FoodView {

    private FoodViewController controller;
    private MediatorView mediator;
    private Stage stage;
    private Label statusLabel;

    private FoodViewController getController() {
        if (controller == null) {
            controller = new FoodViewController(this);
        }
        return controller;
    }

    private void createStageIfNeeded() {
        if (stage != null) {
            return;
        }

        Label header = new Label("=== FOOD MENU ===");
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Button btnShowList = new Button("Show Food List");
        Button btnExit = new Button("Exit");

        btnShowList.setOnAction(e -> getController().processMenuOption("1"));
        btnExit.setOnAction(e -> getController().processMenuOption("2"));

        statusLabel = new Label(" ");

        VBox root = new VBox(12, header, btnShowList, btnExit, statusLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(16));

        stage = new Stage();
        stage.setTitle("Food Manager");
        stage.setScene(new Scene(root, 430, 280));
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
            if (statusLabel != null) {
                statusLabel.setText("Exiting Food View...");
            }
            if (stage != null) {
                stage.close();
            }
        });
    }

    @Override
    public void setMediator(MediatorView mediator) {
        this.mediator = mediator;
    }

    @Override
    public void showFoodList(List<FoodDto> entities) {

    }

    @Override
    public void insertFood() {

    }

    @Override
    public void updateFood() {

    }

    @Override
    public void deleteFood() {

    }

    @Override
    public void showError(String message) {

    }
}

