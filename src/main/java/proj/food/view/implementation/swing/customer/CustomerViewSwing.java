package proj.food.view.implementation.swing.customer;

import proj.food.controller.CustomerViewController;
import proj.food.services.dto.CustomerDto;
import proj.food.view.ViewType;
import proj.food.view.implementation.swing.customer.components.CustomerPanel;
import proj.food.view.interfaces.CustomerView;

import javax.swing.*;
import java.util.List;

public class CustomerViewSwing implements CustomerView {

    private final CustomerPanel panel;
    private CustomerViewController controller;

    public CustomerViewSwing() {
        this.panel = new CustomerPanel();
        wireButtons();
    }

    private CustomerViewController getController() {
        if (controller == null) {
            controller = new CustomerViewController(this);
        }
        return controller;
    }

    private void wireButtons() {
        panel.addShowListListener(e -> getController().processMenuOption("1"));
        panel.addInsertListener(e   -> getController().processMenuOption("2"));
        panel.addUpdateListener(e   -> getController().processMenuOption("3"));
        panel.addDeleteListener(e   -> getController().processMenuOption("4"));
        panel.addExitListener(e     -> getController().processMenuOption("5"));
    }

    public CustomerPanel getPanel() { return panel; }

    @Override
    public void show() { panel.setVisible(true); }

    @Override
    public void exit() {
        panel.setVisible(false);
    }
    @Override
    public void showCustomerList(List<CustomerDto> entities) {
        panel.getTablePanel().clear();
        if (entities.isEmpty()) {
            panel.setStatus("No se encontraron clientes.");
            return;
        }
        panel.getTablePanel().populate(entities);
        panel.setStatus(entities.size() + " cliente(s) cargado(s).");
    }

    @Override
    public void insertCustomer() {
        String name = JOptionPane.showInputDialog(panel, "Nombre:", "Insertar Cliente", JOptionPane.PLAIN_MESSAGE);
        if (name == null) return;
        String cleanName = name.trim();
        if (cleanName.isEmpty()) { showError("El nombre es obligatorio"); return; }

        getController().insertCustomer(new CustomerDto(null, cleanName));
    }

    @Override
    public void updateCustomer() {
        int row = panel.getTablePanel().getSelectedRow();
        if (row < 0) { showError("Seleccione un cliente de la tabla para actualizar"); return; }

        Long id         = Long.valueOf(panel.getTablePanel().getValueAt(row, 0).toString());
        Object nameObj  = panel.getTablePanel().getValueAt(row, 1);

        String newName = JOptionPane.showInputDialog(panel, "Nuevo Nombre:", nameObj == null ? "" : nameObj.toString());
        if (newName == null) return;
        String cleanName = newName.trim();
        if (cleanName.isEmpty()) { showError("El nombre es obligatorio"); return; }

        getController().updateCustomer(new CustomerDto(id, cleanName));
    }

    @Override
    public void deleteCustomer() {
        int row = panel.getTablePanel().getSelectedRow();
        if (row < 0) { showError("Seleccione un cliente de la tabla para eliminar"); return; }

        Long id = Long.valueOf(panel.getTablePanel().getValueAt(row, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(
                panel, "¿Eliminar cliente #" + id + "?", "Eliminar Cliente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.OK_OPTION) {
            getController().deleteCustomer(new CustomerDto(id, null));
        }
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(panel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}