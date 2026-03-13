package proj.food.vista.implementation.swing.food.components;

import proj.food.services.dto.FoodDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FoodTablePanel extends JScrollPane {

    private final DefaultTableModel tableModel;
    private final JTable table;

    public FoodTablePanel() {
        this.tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        this.table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);
        setViewportView(table);
    }

    public void populate(List<FoodDto> entities) {
        tableModel.setRowCount(0);
        entities.forEach(e -> tableModel.addRow(new Object[]{e.id(), e.name(), e.price()}));
    }

    public void clear() {
        tableModel.setRowCount(0);
    }

    /** @return -1 si no hay fila seleccionada */
    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public Object getValueAt(int row, int col) {
        return tableModel.getValueAt(row, col);
    }
}