package proj.food.vista.implementation.customer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proj.food.controller.CustomerViewController;
import proj.food.services.dto.CustomerDto;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.fx.FxRuntime;
import proj.food.vista.interfaces.CustomerView;
import proj.food.vista.mediatr.MediatorView;

import java.util.List;
import java.util.Optional;

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
        Button btnInsert = new Button("Insert Customer");
        Button btnUpdate = new Button("Update Customer");
        Button btnDelete = new Button("Delete Customer");
        Button btnExit = new Button("Exit");

        btnShowList.setOnAction(e -> getController().processMenuOption("1"));
        btnInsert.setOnAction(e -> getController().processMenuOption("2"));
        btnUpdate.setOnAction(e -> getController().processMenuOption("3"));
        btnDelete.setOnAction(e -> getController().processMenuOption("4"));
        btnExit.setOnAction(e -> getController().processMenuOption("5"));

        HBox buttonBar = new HBox(10, btnShowList, btnInsert, btnUpdate, btnDelete, btnExit);
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
        stage.setScene(new Scene(root, 780, 440));
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
        FxRuntime.runOnFxThread(() -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Insert Customer");
            dialog.setHeaderText("Create new customer");
            dialog.setContentText("Name:");

            Optional<String> result = dialog.showAndWait();
            if (result.isEmpty()) {
                return;
            }

            String name = result.get().trim();
            if (name.isEmpty()) {
                showError("Name is required");
                return;
            }

            getController().insertCustomer(new CustomerDto(null, name));
        });
    }

    @Override
    public void updateCustomer() {
        FxRuntime.runOnFxThread(() -> {
            CustomerDto selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Select a customer from the table to update");
                return;
            }

            TextInputDialog dialog = new TextInputDialog(selected.name());
            dialog.setTitle("Update Customer");
            dialog.setHeaderText("Update customer #" + selected.id());
            dialog.setContentText("New name:");

            Optional<String> result = dialog.showAndWait();
            if (result.isEmpty()) {
                return;
            }

            String newName = result.get().trim();
            if (newName.isEmpty()) {
                showError("Name is required");
                return;
            }

            getController().updateCustomer(new CustomerDto(selected.id(), newName));
        });
    }

    @Override
    public void deleteCustomer() {
        FxRuntime.runOnFxThread(() -> {
            CustomerDto selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Select a customer from the table to delete");
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Delete Customer");
            confirm.setHeaderText("Delete customer #" + selected.id());
            confirm.setContentText("Are you sure?");

            Optional<ButtonType> choice = confirm.showAndWait();
            if (choice.isPresent() && choice.get() == ButtonType.OK) {
                getController().deleteCustomer(new CustomerDto(selected.id(), null));
            }
        });
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
                stage.hide();
            }
            if (mediator != null) {
                mediator.changeView(ViewType.START);
            }
        });
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}
