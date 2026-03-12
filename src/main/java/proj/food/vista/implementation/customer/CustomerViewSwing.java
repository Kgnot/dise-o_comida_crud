package proj.food.vista.implementation.customer;

import proj.food.controller.CustomerViewController;
import proj.food.entity.CustomerEntity;
import proj.food.vista.interfaces.CustomerView;
import proj.food.vista.mediatr.MediatorView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerViewSwing extends JFrame implements CustomerView {

    private CustomerViewController controller;
    private final DefaultTableModel tableModel;
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
        setSize(550, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("=== CUSTOMER MENU ===", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JTable table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);

        JButton btnShowList = new JButton("Show Customer List");
        JButton btnExit = new JButton("Exit");

        btnShowList.addActionListener(e -> getController().processMenuOption("1"));
        btnExit.addActionListener(e -> getController().processMenuOption("2"));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        buttonPanel.add(btnShowList);
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
    public void showCustomerList(List<CustomerEntity> entities) {
        tableModel.setRowCount(0);
        if (entities.isEmpty()) {
            statusLabel.setText("No customers found.");
        } else {
            entities.forEach(e -> tableModel.addRow(new Object[]{e.getId(), e.getName()}));
            statusLabel.setText(entities.size() + " customer(s) loaded.");
        }
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void exit() {
        dispose();
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}

