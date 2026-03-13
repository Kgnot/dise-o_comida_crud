package proj.food.view.implementation.fx.customer.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import proj.food.services.dto.CustomerDto;

import java.util.List;

public class CustomerTablePane extends VBox {

    private final TableView<CustomerDto> tableView = new TableView<>();

    public CustomerTablePane() {
        buildUI();
    }

    private void buildUI() {
        TableColumn<CustomerDto, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().id() == null ? "" : c.getValue().id().toString()));
        colId.setPrefWidth(80);

        TableColumn<CustomerDto, String> colName = new TableColumn<>("Nombre");
        colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().name() == null ? "" : c.getValue().name()));
        colName.setPrefWidth(320);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(colId, colName);

        getChildren().add(tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
    }

    public void populate(List<CustomerDto> entities) {
        tableView.getItems().setAll(entities);
    }

    public void clear() {
        tableView.getItems().clear();
    }

    public CustomerDto getSelectedItem() {
        return tableView.getSelectionModel().getSelectedItem();
    }
}

