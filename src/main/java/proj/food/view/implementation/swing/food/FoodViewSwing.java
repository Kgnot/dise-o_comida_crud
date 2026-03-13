package proj.food.view.implementation.swing.food;

import proj.food.controller.FoodViewController;
import proj.food.services.dto.FoodDto;
import proj.food.view.implementation.swing.food.components.FoodPanel;
import proj.food.view.interfaces.FoodView;

import javax.swing.*;
import java.util.List;

public class FoodViewSwing implements FoodView {

    private final FoodPanel panel;
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
    public void show() {
        panel.setVisible(true);
    }

    @Override
    public void exit() {
        panel.setVisible(false);
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
        String name = promptName("Insertar Comida", null);
        if (name == null) return;

        Double price = promptPrice("Insertar Comida", null);
        if (price == null) return;

        getController().insertFood(new FoodDto(null, name, price));
    }

    @Override
    public void updateFood() {
        int row = panel.getTablePanel().getSelectedRow();
        if (row < 0) {
            showError("Seleccione una comida de la tabla para actualizar");
            return;
        }

        Long id = Long.valueOf(panel.getTablePanel().getValueAt(row, 0).toString());
        String name = promptName("Actualizar Comida", panel.getTablePanel().getValueAt(row, 1));
        if (name == null) return;

        Double price = promptPrice("Actualizar Comida", panel.getTablePanel().getValueAt(row, 2));
        if (price == null) return;

        getController().updateFood(new FoodDto(id, name, price));
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
    private String promptName(String title, Object current) {
        String raw = JOptionPane.showInputDialog(panel, "Nombre:",
                current == null ? "" : current.toString(), JOptionPane.PLAIN_MESSAGE);
        if (raw == null) return null;
        String clean = raw.trim();
        if (clean.isEmpty()) { showError("El nombre es obligatorio"); return null; }
        return clean;
    }

    private Double promptPrice(String title, Object current) {
        String raw = JOptionPane.showInputDialog(panel, "Precio:",
                current == null ? "" : current.toString(), JOptionPane.PLAIN_MESSAGE);
        if (raw == null) return null;
        return parsePrice(raw);
    }

    private Double parsePrice(String text) {
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            showError("El precio debe ser un número");
            return null;
        }
    }
}