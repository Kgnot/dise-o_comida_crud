package proj.food.vista.implementation.food;

import proj.food.controller.FoodViewController;
import proj.food.services.dto.FoodDto;
import proj.food.vista.ViewType;
import proj.food.vista.interfaces.FoodView;
import proj.food.vista.mediatr.MediatorView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FoodViewSwing extends JFrame implements FoodView {

    private FoodViewController controller;
    private MediatorView mediator;
    private final JLabel statusLabel = new JLabel(" ");
    private final DefaultTableModel tableModel;
    private final JTable table;

    public FoodViewSwing() {
        this.tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(tableModel);
        buildUI();
    }

    private FoodViewController getController() {
        if (controller == null) {
            controller = new FoodViewController(this);
        }
        return controller;
    }

    private void buildUI() {
        setTitle("Food Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 430);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("=== FOOD MENU ===", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));
        header.setBorder(BorderFactory.createEmptyBorder(12, 8, 6, 8));

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);

        JButton btnShowList = new JButton("Show Food List");
        JButton btnInsert = new JButton("Insert Food");
        JButton btnUpdate = new JButton("Update Food");
        JButton btnDelete = new JButton("Delete Food");
        JButton btnExit = new JButton("Exit");

        btnShowList.addActionListener(e -> getController().processMenuOption("1"));
        btnInsert.addActionListener(e -> getController().processMenuOption("2"));
        btnUpdate.addActionListener(e -> getController().processMenuOption("3"));
        btnDelete.addActionListener(e -> getController().processMenuOption("4"));
        btnExit.addActionListener(e -> getController().processMenuOption("5"));

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        actionsPanel.add(btnShowList);
        actionsPanel.add(btnInsert);
        actionsPanel.add(btnUpdate);
        actionsPanel.add(btnDelete);
        actionsPanel.add(btnExit);

        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 12, 8, 12));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(actionsPanel, BorderLayout.NORTH);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        add(header, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void showMenu() {
        setVisible(true);
    }

    @Override
    public void exit() {
        setVisible(false);
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
        tableModel.setRowCount(0);
        if (entities.isEmpty()) {
            statusLabel.setText("No foods found.");
            return;
        }

        entities.forEach(e -> tableModel.addRow(new Object[]{e.id(), e.name(), e.price()}));
        statusLabel.setText(entities.size() + " food item(s) loaded.");
    }

    @Override
    public void insertFood() {
        String name = JOptionPane.showInputDialog(this, "Name:", "Insert Food", JOptionPane.PLAIN_MESSAGE);
        if (name == null) {
            return;
        }

        String cleanName = name.trim();
        if (cleanName.isEmpty()) {
            showError("Name is required");
            return;
        }

        String priceText = JOptionPane.showInputDialog(this, "Price:", "Insert Food", JOptionPane.PLAIN_MESSAGE);
        if (priceText == null) {
            return;
        }

        Double price;
        try {
            price = Double.parseDouble(priceText.trim());
        } catch (NumberFormatException e) {
            showError("Price must be a number");
            return;
        }

        getController().insertFood(new FoodDto(null, cleanName, price));
    }

    @Override
    public void updateFood() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showError("Select a food from the table to update");
            return;
        }

        Long id = Long.valueOf(tableModel.getValueAt(selectedRow, 0).toString());
        Object currentName = tableModel.getValueAt(selectedRow, 1);
        Object currentPrice = tableModel.getValueAt(selectedRow, 2);

        String name = JOptionPane.showInputDialog(this, "New name:", currentName == null ? "" : currentName.toString());
        if (name == null) {
            return;
        }

        String cleanName = name.trim();
        if (cleanName.isEmpty()) {
            showError("Name is required");
            return;
        }

        String priceText = JOptionPane.showInputDialog(this, "New price:", currentPrice == null ? "" : currentPrice.toString());
        if (priceText == null) {
            return;
        }

        Double price;
        try {
            price = Double.parseDouble(priceText.trim());
        } catch (NumberFormatException e) {
            showError("Price must be a number");
            return;
        }

        getController().updateFood(new FoodDto(id, cleanName, price));
    }

    @Override
    public void deleteFood() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showError("Select a food from the table to delete");
            return;
        }

        Long id = Long.valueOf(tableModel.getValueAt(selectedRow, 0).toString());
        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Delete food #" + id + "?",
                "Delete Food",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmation == JOptionPane.OK_OPTION) {
            getController().deleteFood(new FoodDto(id, null, null));
        }
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
