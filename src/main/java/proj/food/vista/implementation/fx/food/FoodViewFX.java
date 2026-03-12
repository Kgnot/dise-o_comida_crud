package proj.food.vista.implementation.fx.food;

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
import proj.food.controller.FoodViewController;
import proj.food.services.dto.FoodDto;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.fx.FxRuntime;
import proj.food.vista.interfaces.FoodView;
import proj.food.vista.mediatr.MediatorView;

import java.util.List;
import java.util.Optional;

public class FoodViewFX implements FoodView {

    private FoodViewController controller;
    private MediatorView mediator;
    private Stage stage;
    private Label statusLabel;
    private TableView<FoodDto> tableView;

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

        TableColumn<FoodDto, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().id() == null ? "" : c.getValue().id().toString()));
        colId.setPrefWidth(80);

        TableColumn<FoodDto, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().name() == null ? "" : c.getValue().name()));
        colName.setPrefWidth(220);

        TableColumn<FoodDto, String> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().price() == null ? "" : c.getValue().price().toString()));
        colPrice.setPrefWidth(120);

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(colId, colName, colPrice);

        Button btnShowList = new Button("Show Food List");
        Button btnInsert = new Button("Insert Food");
        Button btnUpdate = new Button("Update Food");
        Button btnDelete = new Button("Delete Food");
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

        Label header = new Label("=== FOOD MENU ===");
        header.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        VBox root = new VBox(10, header, tableView, buttonBar, statusLabel);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(16));
        VBox.setVgrow(tableView, Priority.ALWAYS);

        stage = new Stage();
        stage.setTitle("Food Manager");
        stage.setScene(new Scene(root, 820, 430));
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
                stage.hide();
            }
            if (mediator != null) {
                mediator.changeView(ViewType.START);
            }
        });
    }

    @Override
    public void setMediator(MediatorView mediator) {
        this.mediator = mediator;
    }

    @Override
    public void showFoodList(List<FoodDto> entities) {
        FxRuntime.runOnFxThread(() -> {
            ObservableList<FoodDto> data = FXCollections.observableArrayList(entities);
            tableView.setItems(data);
            statusLabel.setText(entities.isEmpty()
                    ? "No foods found."
                    : entities.size() + " food item(s) loaded.");
        });
    }

    @Override
    public void insertFood() {
        FxRuntime.runOnFxThread(() -> {
            Optional<String> name = askText("Insert Food", "Create new food", "Name:", "");
            if (name.isEmpty() || name.get().isBlank()) {
                if (name.isPresent()) {
                    showError("Name is required");
                }
                return;
            }

            Optional<Double> price = askPrice("Insert Food", "Price:", "");
            if (price.isEmpty()) {
                return;
            }

            getController().insertFood(new FoodDto(null, name.get().trim(), price.get()));
        });
    }

    @Override
    public void updateFood() {
        FxRuntime.runOnFxThread(() -> {
            FoodDto selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Select a food from the table to update");
                return;
            }

            Optional<String> name = askText("Update Food", "Update food #" + selected.id(), "New name:", selected.name());
            if (name.isEmpty() || name.get().isBlank()) {
                if (name.isPresent()) {
                    showError("Name is required");
                }
                return;
            }

            String initialPrice = selected.price() == null ? "" : selected.price().toString();
            Optional<Double> price = askPrice("Update Food", "New price:", initialPrice);
            if (price.isEmpty()) {
                return;
            }

            getController().updateFood(new FoodDto(selected.id(), name.get().trim(), price.get()));
        });
    }

    @Override
    public void deleteFood() {
        FxRuntime.runOnFxThread(() -> {
            FoodDto selected = tableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showError("Select a food from the table to delete");
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Delete Food");
            confirm.setHeaderText("Delete food #" + selected.id());
            confirm.setContentText("Are you sure?");

            Optional<ButtonType> choice = confirm.showAndWait();
            if (choice.isPresent() && choice.get() == ButtonType.OK) {
                getController().deleteFood(new FoodDto(selected.id(), null, null));
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

    private Optional<String> askText(String title, String header, String content, String initialValue) {
        TextInputDialog dialog = new TextInputDialog(initialValue == null ? "" : initialValue);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        return dialog.showAndWait().map(String::trim);
    }

    private Optional<Double> askPrice(String title, String content, String initialValue) {
        TextInputDialog dialog = new TextInputDialog(initialValue == null ? "" : initialValue);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(content);

        Optional<String> raw = dialog.showAndWait();
        if (raw.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(Double.parseDouble(raw.get().trim()));
        } catch (NumberFormatException e) {
            showError("Price must be a number");
            return Optional.empty();
        }
    }
}
