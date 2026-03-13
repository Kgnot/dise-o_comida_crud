package proj.food.vista.implementation.fx.customer;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import proj.food.controller.CustomerViewController;
import proj.food.services.dto.CustomerDto;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.fx.FxRuntime;
import proj.food.vista.implementation.fx.customer.components.CustomerPane;
import proj.food.vista.interfaces.CustomerView;
import proj.food.vista.mediatr.MediatorView;

import java.util.List;
import java.util.Optional;

public class CustomerViewFX implements CustomerView {

    private final CustomerPane pane;
    private CustomerViewController controller;
    private MediatorView mediator;

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
    public void showMenu() {
        pane.setVisible(true);
    }

    @Override
    public void showCustomerList(List<CustomerDto> entities) {
        FxRuntime.runOnFxThread(() -> {
            pane.getTablePane().clear();
            if (entities.isEmpty()) {
                pane.setStatus("No customers found.");
                return;
            }
            // Rebuild the table from DTOs returned by the controller layer.
            pane.getTablePane().populate(entities);
            pane.setStatus(entities.size() + " customer(s) loaded.");
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
            CustomerDto selected = pane.getTablePane().getSelectedItem();
            // Updates operate only on the currently selected row.
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
            CustomerDto selected = pane.getTablePane().getSelectedItem();
            // Deletion also depends on table selection to avoid accidental IDs.
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
        pane.setVisible(false);
        if (mediator != null) {
            mediator.changeView(ViewType.START);
        }
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}
