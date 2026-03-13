package proj.food.vista.implementation.fx.food.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import proj.food.vista.implementation.fx.shared.StatusBarFX;

public class FoodPane extends BorderPane {

    private final FoodTablePane tablePane = new FoodTablePane();
    private final StatusBarFX statusBar = new StatusBarFX();

    private final Button btnShowList = new Button("Mostrar Lista de Comida");
    private final Button btnInsert = new Button("Insertar Comida");
    private final Button btnUpdate = new Button("Actualizar Comida");
    private final Button btnDelete = new Button("Eliminar Comida");
    private final Button btnExit = new Button("Salir");

    public FoodPane() {
        buildUI();
    }

    private void buildUI() {
        setPadding(new Insets(20)); // Generous padding
        setStyle("-fx-background-color: white;"); // Ensure background is white

        Label header = new Label("Menú de Comida");
        header.getStyleClass().add("header-label");

        BorderPane.setAlignment(header, Pos.TOP_LEFT);
        BorderPane.setMargin(header, new Insets(0, 0, 20, 0));
        setTop(header);

        setCenter(tablePane);

        // Apply styles to buttons
        btnInsert.getStyleClass().addAll("action-button", "primary");
        btnUpdate.getStyleClass().addAll("action-button", "primary");
        btnDelete.getStyleClass().addAll("action-button", "danger");
        btnShowList.getStyleClass().add("action-button");
        btnExit.getStyleClass().add("action-button");

        HBox actions = new HBox(12, btnShowList, btnInsert, btnUpdate, btnDelete, btnExit);
        actions.setAlignment(Pos.CENTER_RIGHT); // Align actions to the right
        actions.setPadding(new Insets(16, 0, 0, 0));

        VBox bottom = new VBox(actions, statusBar);
        bottom.setSpacing(10);
        VBox.setVgrow(statusBar, Priority.NEVER);
        setBottom(bottom);
    }

    public void addShowListHandler(EventHandler<ActionEvent> handler) { btnShowList.setOnAction(handler); }
    public void addInsertHandler(EventHandler<ActionEvent> handler) { btnInsert.setOnAction(handler); }
    public void addUpdateHandler(EventHandler<ActionEvent> handler) { btnUpdate.setOnAction(handler); }
    public void addDeleteHandler(EventHandler<ActionEvent> handler) { btnDelete.setOnAction(handler); }
    public void addExitHandler(EventHandler<ActionEvent> handler) { btnExit.setOnAction(handler); }

    public FoodTablePane getTablePane() { return tablePane; }

    public void setStatus(String message) { statusBar.setText(message); }
}

