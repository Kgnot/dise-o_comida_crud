package proj.food.vista.implementation.fx.customer.components;

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
        setPadding(new Insets(10));

        Label header = new Label("=== MENÚ DE CLIENTES ===");
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        BorderPane.setAlignment(header, Pos.CENTER);
        BorderPane.setMargin(header, new Insets(12, 8, 6, 8));
        setTop(header);

        setCenter(tablePane);

        HBox actions = new HBox(10, btnShowList, btnInsert, btnUpdate, btnDelete, btnExit);
        actions.setAlignment(Pos.CENTER);
        actions.setPadding(new Insets(8));

        VBox bottom = new VBox(actions, statusBar);
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

