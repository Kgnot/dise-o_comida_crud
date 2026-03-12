package proj.food.vista.implementation.customer;

import proj.food.controller.CustomerViewController;
import proj.food.services.dto.CustomerDto;
import proj.food.vista.ViewType;
import proj.food.vista.interfaces.CustomerView;
import proj.food.vista.mediatr.MediatorView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerViewSwing extends JFrame implements CustomerView {

    private CustomerViewController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JLabel statusLabel;
    // mediator
    private MediatorView mediator;

    public CustomerViewSwing() {
        this.tableModel = new DefaultTableModel(new String[]{"ID", "Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(tableModel);
        this.statusLabel = new JLabel(" ");
        buildUI();
    }

    public CustomerViewController getController() {
        if (controller == null) {
            controller = new CustomerViewController(this);
        }
        return controller;
    }

    private void buildUI() {
        setTitle("Customer Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("=== CUSTOMER MENU ===", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);

        JButton btnShowList = new JButton("Show Customer List");
        JButton btnInsert = new JButton("Insert Customer");
        JButton btnUpdate = new JButton("Update Customer");
        JButton btnDelete = new JButton("Delete Customer");
        JButton btnExit = new JButton("Exit");

        btnShowList.addActionListener(e -> getController().processMenuOption("1"));
        btnInsert.addActionListener(e -> getController().processMenuOption("2"));
        btnUpdate.addActionListener(e -> getController().processMenuOption("3"));
        btnDelete.addActionListener(e -> getController().processMenuOption("4"));
        btnExit.addActionListener(e -> getController().processMenuOption("5"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        buttonPanel.add(btnShowList);
        buttonPanel.add(btnInsert);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnExit);

        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 8, 4, 0));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
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
    public void showCustomerList(List<CustomerDto> entities) {
        tableModel.setRowCount(0);
        if (entities.isEmpty()) {
            statusLabel.setText("No customers found.");
        } else {
            entities.forEach(e -> tableModel.addRow(new Object[]{e.id(), e.name()}));
            statusLabel.setText(entities.size() + " customer(s) loaded.");
        }
    }

    @Override
    public void insertCustomer() {
        String name = JOptionPane.showInputDialog(this, "Name:", "Insert Customer", JOptionPane.PLAIN_MESSAGE);
        if (name == null) {
            return;
        }

        String cleanName = name.trim();
        if (cleanName.isEmpty()) {
            showError("Name is required");
            return;
        }

        getController().insertCustomer(new CustomerDto(null, cleanName));
    }

    @Override
    public void updateCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showError("Select a customer from the table to update");
            return;
        }

        Object idObj = tableModel.getValueAt(selectedRow, 0);
        Object nameObj = tableModel.getValueAt(selectedRow, 1);
        Long id = Long.valueOf(idObj.toString());

        String newName = JOptionPane.showInputDialog(this, "New Name:", nameObj == null ? "" : nameObj.toString());
        if (newName == null) {
            return;
        }

        String cleanName = newName.trim();
        if (cleanName.isEmpty()) {
            showError("Name is required");
            return;
        }

        getController().updateCustomer(new CustomerDto(id, cleanName));
    }

    @Override
    public void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            showError("Select a customer from the table to delete");
            return;
        }

        Long id = Long.valueOf(tableModel.getValueAt(selectedRow, 0).toString());
        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Delete customer #" + id + "?",
                "Delete Customer",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmation == JOptionPane.OK_OPTION) {
            getController().deleteCustomer(new CustomerDto(id, null));
        }
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void exit() {
        setVisible(false);
        if (mediator != null) {
            mediator.changeView(ViewType.START);
        }
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}
