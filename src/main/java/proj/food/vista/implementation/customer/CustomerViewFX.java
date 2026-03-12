package proj.food.vista.implementation.customer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proj.food.controller.CustomerViewController;
import proj.food.services.dto.CustomerDto;
import proj.food.vista.implementation.fx.FxRuntime;
import proj.food.vista.interfaces.CustomerView;
import proj.food.vista.mediatr.MediatorView;

import java.util.List;

public class CustomerViewFX implements CustomerView {

    private CustomerViewController controller;
    private MediatorView mediator;
    private TableView<CustomerDto> tableView;
    private Label statusLabel;
    private Stage stage;

    private CustomerViewController getController() {
        if (controller == null) {
            controller = new CustomerViewController(this);
        }
        return controller;
    }

    private void createStageIfNeeded() {
        if (stage != null) {
            return;
        }

        TableColumn<CustomerDto, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().id() == null ? "" : c.getValue().id().toString()));
        colId.setPrefWidth(80);

        TableColumn<CustomerDto, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().name()));
        colName.setPrefWidth(300);

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(colId, colName);

        Button btnShowList = new Button("Show Customer List");
        Button btnExit = new Button("Exit");

        btnShowList.setOnAction(e -> getController().processMenuOption("1"));
        btnExit.setOnAction(e -> getController().processMenuOption("2"));

        HBox buttonBar = new HBox(12, btnShowList, btnExit);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(8, 0, 4, 0));

        statusLabel = new Label(" ");

        Label header = new Label("=== CUSTOMER MENU ===");
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        VBox root = new VBox(10, header, tableView, buttonBar, statusLabel);
        root.setPadding(new Insets(14));
        VBox.setVgrow(tableView, Priority.ALWAYS);

        stage = new Stage();
        stage.setTitle("Customer Manager");
        stage.setScene(new Scene(root, 550, 420));
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
    public void showCustomerList(List<CustomerDto> entities) {
        FxRuntime.runOnFxThread(() -> {
            ObservableList<CustomerDto> data = FXCollections.observableArrayList(entities);
            tableView.setItems(data);
            statusLabel.setText(entities.isEmpty()
                    ? "No customers found."
                    : entities.size() + " customer(s) loaded.");
        });
    }

    @Override
    public void insertCustomer() {

    }

    @Override
    public void updateCustomer() {

    }

    @Override
    public void deleteCustomer() {

    }

    @Override
    public void showError(String message) {
        FxRuntime.runOnFxThread(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    @Override
    public void exit() {
        FxRuntime.runOnFxThread(() -> {
            if (stage != null) {
                stage.close();
            }
        });
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}
