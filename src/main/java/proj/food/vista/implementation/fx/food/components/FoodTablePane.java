package proj.food.vista.implementation.fx.food.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import proj.food.services.dto.FoodDto;

import java.util.List;

public class FoodTablePane extends VBox {

    private final TableView<FoodDto> tableView = new TableView<>();

    public FoodTablePane() {
        buildUI();
    }

    private void buildUI() {
        TableColumn<FoodDto, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().id() == null ? "" : c.getValue().id().toString()));
        colId.setPrefWidth(80);

        TableColumn<FoodDto, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().name() == null ? "" : c.getValue().name()));
        colName.setPrefWidth(240);

        TableColumn<FoodDto, String> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().price() == null ? "" : c.getValue().price().toString()));
        colPrice.setPrefWidth(140);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(colId, colName, colPrice);

        getChildren().add(tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
    }

    public void populate(List<FoodDto> entities) {
        tableView.getItems().setAll(entities);
    }

    public void clear() {
        tableView.getItems().clear();
    }

    public FoodDto getSelectedItem() {
        return tableView.getSelectionModel().getSelectedItem();
    }
}

