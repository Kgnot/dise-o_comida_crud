package proj.food.vista.implementation.swing.food;

import proj.food.controller.FoodViewController;
import proj.food.services.dto.FoodDto;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.swing.food.components.FoodPanel;
import proj.food.vista.interfaces.FoodView;
import proj.food.vista.mediatr.MediatorView;

import javax.swing.*;
import java.util.List;

public class FoodViewSwing implements FoodView {

    private final FoodPanel panel;
    private MediatorView mediator;
    private FoodViewController controller;

    public FoodViewSwing() {
        this.panel = new FoodPanel();
        wireButtons();
    }

    private FoodViewController getController() {
        if (controller == null) {
            controller = new FoodViewController(this);
        }
        return controller;
    }

    private void wireButtons() {
        panel.addShowListListener(e -> getController().processMenuOption("1"));
        panel.addInsertListener(e -> getController().processMenuOption("2"));
        panel.addUpdateListener(e -> getController().processMenuOption("3"));
        panel.addDeleteListener(e -> getController().processMenuOption("4"));
        panel.addExitListener(e -> getController().processMenuOption("5"));
    }

    public FoodPanel getPanel() {
        return panel;
    }

    @Override
    public void showMenu() {
        panel.setVisible(true);
    }

    @Override
    public void exit() {
        panel.setVisible(false);
        if (mediator != null) mediator.changeView(ViewType.START);
    }

    @Override
    public void setMediator(MediatorView mediator) {
        this.mediator = mediator;
    }

    @Override
    public void showFoodList(List<FoodDto> entities) {
        panel.getTablePanel().clear();
        if (entities.isEmpty()) {
            panel.setStatus("No se encontró comida.");
            return;
        }
        panel.getTablePanel().populate(entities);
        panel.setStatus(entities.size() + " elemento(s) de comida cargado(s).");
    }

    @Override
    public void insertFood() {
        String name = JOptionPane.showInputDialog(panel, "Nombre:", "Insertar Comida", JOptionPane.PLAIN_MESSAGE);
        if (name == null) return;
        String cleanName = name.trim();
        if (cleanName.isEmpty()) {
            showError("El nombre es obligatorio");
            return;
        }
        // Price input
        String priceText = JOptionPane.showInputDialog(panel, "Precio:", "Insertar Comida", JOptionPane.PLAIN_MESSAGE);
        if (priceText == null) return;
        Double price = parsePrice(priceText);
        if (price == null) return;
        getController().insertFood(new FoodDto(null, cleanName, price));
    }

    @Override
    public void updateFood() {
        int row = panel.getTablePanel().getSelectedRow();
        // The selected row is the source of truth for id and current values.
        if (row < 0) {
            showError("Seleccione una comida de la tabla para actualizar");
            return;
        }

        Long id = Long.valueOf(panel.getTablePanel().getValueAt(row, 0).toString());
        Object currentName = panel.getTablePanel().getValueAt(row, 1);
        Object currentPrice = panel.getTablePanel().getValueAt(row, 2);
        // Prompt the user for new name and price, pre-filling with current values
        String name = JOptionPane.showInputDialog(panel, "Nuevo nombre:", currentName == null ? "" : currentName.toString());
        if (name == null) return;
        String cleanName = name.trim();
        if (cleanName.isEmpty()) {
            showError("El nombre es obligatorio");
            return;
        }
        // Price input with current price pre-filled
        String priceText = JOptionPane.showInputDialog(panel, "Nuevo precio:", currentPrice == null ? "" : currentPrice.toString());
        if (priceText == null) return;
        Double price = parsePrice(priceText);
        if (price == null) return;
        getController().updateFood(new FoodDto(id, cleanName, price));
    }

    @Override
    public void deleteFood() {
        int row = panel.getTablePanel().getSelectedRow();
        if (row < 0) {
            showError("Seleccione una comida de la tabla para eliminar");
            return;
        }

        Long id = Long.valueOf(panel.getTablePanel().getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(
                panel, "¿Eliminar comida #" + id + "?", "Eliminar Comida",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.OK_OPTION) {
            getController().deleteFood(new FoodDto(id, null, null));
        }
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    // Helper method to parse price input
    private Double parsePrice(String text) {
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            showError("El precio debe ser un número");
            return null;
        }
    }
}