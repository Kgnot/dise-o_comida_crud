package proj.food.view.implementation.fx.customer.components;

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
import proj.food.view.implementation.fx.shared.StatusBarFX;

public class CustomerPane extends BorderPane {

    private final CustomerTablePane tablePane = new CustomerTablePane();
    private final StatusBarFX statusBar = new StatusBarFX();

    private final Button btnShowList = new Button("Mostrar Lista de Clientes");
    private final Button btnInsert = new Button("Insertar Cliente");
    private final Button btnUpdate = new Button("Actualizar Cliente");
    private final Button btnDelete = new Button("Eliminar Cliente");
    private final Button btnExit = new Button("Salir");

    public CustomerPane() {
        buildUI();
    }

    private void buildUI() {
        setPadding(new Insets(20));
        setStyle("-fx-background-color: white;");

        Label header = new Label("Menú de Clientes");
        header.getStyleClass().add("header-label");

        BorderPane.setAlignment(header, Pos.TOP_LEFT);
        BorderPane.setMargin(header, new Insets(0, 0, 20, 0));
        setTop(header);

        setCenter(tablePane);

        // Styling buttons
        btnInsert.getStyleClass().addAll("action-button", "primary");
        btnUpdate.getStyleClass().addAll("action-button", "primary");
        btnDelete.getStyleClass().addAll("action-button", "danger");
        btnShowList.getStyleClass().add("action-button");
        btnExit.getStyleClass().add("action-button");

        HBox actions = new HBox(12, btnShowList, btnInsert, btnUpdate, btnDelete, btnExit);
        actions.setAlignment(Pos.CENTER_RIGHT);
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

    public CustomerTablePane getTablePane() { return tablePane; }

    public void setStatus(String message) { statusBar.setText(message); }
}

