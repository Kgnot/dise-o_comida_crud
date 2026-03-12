package proj.food.vista.implementation.fx.food;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import proj.food.controller.FoodViewController;
import proj.food.services.dto.FoodDto;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.fx.FxRuntime;
import proj.food.vista.implementation.fx.food.components.FoodPane;
import proj.food.vista.interfaces.FoodView;
import proj.food.vista.mediatr.MediatorView;

import java.util.List;
import java.util.Optional;

public class FoodViewFX implements FoodView {

    private final FoodPane pane;
    private FoodViewController controller;
    private MediatorView mediator;

    public FoodViewFX() {
        this.pane = new FoodPane();
        wireButtons();
    }

    private FoodViewController getController() {
        if (controller == null) {
            controller = new FoodViewController(this);
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

    public FoodPane getPane() {
        return pane;
    }

    @Override
    public void showMenu() {
        pane.setVisible(true);
    }

    @Override
    public void exit() {
        pane.setVisible(false);
        if (mediator != null) {
            mediator.changeView(ViewType.START);
        }
    }

    @Override
    public void setMediator(MediatorView mediator) {
        this.mediator = mediator;
    }

    @Override
    public void showFoodList(List<FoodDto> entities) {
        FxRuntime.runOnFxThread(() -> {
            pane.getTablePane().clear();
            if (entities.isEmpty()) {
                pane.setStatus("No foods found.");
                return;
            }
            pane.getTablePane().populate(entities);
            pane.setStatus(entities.size() + " food item(s) loaded.");
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
            FoodDto selected = pane.getTablePane().getSelectedItem();
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
            FoodDto selected = pane.getTablePane().getSelectedItem();
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
