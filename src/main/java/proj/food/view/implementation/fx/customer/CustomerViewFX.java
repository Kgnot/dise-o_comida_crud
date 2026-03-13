package proj.food.view.implementation.fx.customer;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import proj.food.controller.CustomerViewController;
import proj.food.services.dto.CustomerDto;
import proj.food.view.ViewType;
import proj.food.view.implementation.fx.FxRuntime;
import proj.food.view.implementation.fx.customer.components.CustomerPane;
import proj.food.view.interfaces.CustomerView;

import java.util.List;
import java.util.Optional;

public class CustomerViewFX implements CustomerView {

    private final CustomerPane pane;
    private CustomerViewController controller;

    public CustomerViewFX() {
        this.pane = new CustomerPane();
        wireButtons();
    }

    private CustomerViewController getController() {
        if (controller == null) {
            // Lazily create the controller when the first action is triggered.
            controller = new CustomerViewController(this);
        }
        return controller;
    }

    private void wireButtons() {
        pane.addShowListHandler(e -> getController().processMenuOption("1"));
        pane.addInsertHandler(e -> getController().processMenuOption("2"));
        pane.addUpdateHandler(e -> getController().processMenuOption("3"));
        pane.addDeleteHandler(e -> getController().processMenuOption("4"));
        pane.addExitHandler(e -> getController().processMenuOption("5"));
    }

    public CustomerPane getPane() {
        return pane;
    }

    @Override
    public void show() {
        pane.setVisible(true);
    }

    @Override
    public void showCustomerList(List<CustomerDto> entities) {
        FxRuntime.runOnFxThread(() -> {
            pane.getTablePane().clear();
            if (entities.isEmpty()) {
                pane.setStatus("No se encontraron clientes.");
                return;
            }
            // Rebuild the table from DTOs returned by the controller layer.
            pane.getTablePane().populate(entities);
            pane.setStatus(entities.size() + " cliente(s) cargado(s).");
        });
    }

    @Override
    public void insertCustomer() {
        FxRuntime.runOnFxThread(() -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Insertar Cliente");
            dialog.setHeaderText("Crear nuevo cliente");
            dialog.setContentText("Nombre:");

            Optional<String> result = dialog.showAndWait();
            if (result.isEmpty()) {
                return;
            }

            String name = result.get().trim();
            if (name.isEmpty()) {
                showError("El nombre es obligatorio");
                return;
            }

            getController().insertCustomer(new CustomerDto(null, name));
        });
    }

    @Override
    public void updateCustomer() {
        FxRuntime.runOnFxThread(() -> {
            CustomerDto selected = pane.getTablePane().getSelectedItem();
            // Updates operate only on the currently selected row.
            if (selected == null) {
                showError("Seleccione un cliente de la tabla para actualizar");
                return;
            }

            TextInputDialog dialog = new TextInputDialog(selected.name());
            dialog.setTitle("Actualizar Cliente");
            dialog.setHeaderText("Actualizar cliente #" + selected.id());
            dialog.setContentText("Nuevo nombre:");

            Optional<String> result = dialog.showAndWait();
            if (result.isEmpty()) {
                return;
            }

            String newName = result.get().trim();
            if (newName.isEmpty()) {
                showError("El nombre es obligatorio");
                return;
            }

            getController().updateCustomer(new CustomerDto(selected.id(), newName));
        });
    }

    @Override
    public void deleteCustomer() {
        FxRuntime.runOnFxThread(() -> {
            CustomerDto selected = pane.getTablePane().getSelectedItem();
            // Deletion also depends on table selection to avoid accidental IDs.
            if (selected == null) {
                showError("Seleccione un cliente de la tabla para eliminar");
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Eliminar Cliente");
            confirm.setHeaderText("Eliminar cliente #" + selected.id());
            confirm.setContentText("¿Está seguro?");

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
        pane.setVisible(false);
    }

}
