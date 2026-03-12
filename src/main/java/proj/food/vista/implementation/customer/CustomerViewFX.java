package proj.food.vista.implementation.customer;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import proj.food.controller.CustomerViewController;
import proj.food.entity.CustomerEntity;
import proj.food.vista.interfaces.CustomerView;
import proj.food.vista.mediatr.MediatorView;

import java.util.List;

public class CustomerViewFX extends Application implements CustomerView {

    private CustomerViewController controller;
    private MediatorView mediator;
    private TableView<CustomerEntity> tableView;
    private Label statusLabel;
    private Stage stage;

    // JavaFX necesita un constructor sin args para Application.launch()
    // El controller se inicializa en start()
    public CustomerViewFX() {
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        this.controller = new CustomerViewController(this);

        // ── Tabla ──────────────────────────────────────────────
        TableColumn<CustomerEntity, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getId() == null ? "" : c.getValue().getId().toString()));
        colId.setPrefWidth(80);

        TableColumn<CustomerEntity, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        colName.setPrefWidth(300);

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(colId, colName);

        // ── Botones ────────────────────────────────────────────
        Button btnShowList = new Button("Show Customer List");
        Button btnExit = new Button("Exit");

        btnShowList.setOnAction(e -> controller.processMenuOption("1"));
        btnExit.setOnAction(e -> controller.processMenuOption("2"));

        HBox buttonBar = new HBox(12, btnShowList, btnExit);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(8, 0, 4, 0));

        // ── Status ─────────────────────────────────────────────
        statusLabel = new Label(" ");

        // ── Layout ─────────────────────────────────────────────
        Label header = new Label("=== CUSTOMER MENU ===");
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        VBox root = new VBox(10, header, tableView, buttonBar, statusLabel);
        root.setPadding(new Insets(14));
        VBox.setVgrow(tableView, Priority.ALWAYS);

        primaryStage.setTitle("Customer Manager");
        primaryStage.setScene(new Scene(root, 550, 420));
        primaryStage.show();
    }

    // ── CustomerView ───────────────────────────────────────────

    @Override
    public void showMenu() {
        Application.launch(CustomerViewFX.class);
    }

    @Override
    public void showCustomerList(List<CustomerEntity> entities) {
        ObservableList<CustomerEntity> data = FXCollections.observableArrayList(entities);
        tableView.setItems(data);
        statusLabel.setText(entities.isEmpty()
                ? "No customers found."
                : entities.size() + " customer(s) loaded.");
    }

    @Override
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void exit() {
        if (stage != null) stage.close();
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}

