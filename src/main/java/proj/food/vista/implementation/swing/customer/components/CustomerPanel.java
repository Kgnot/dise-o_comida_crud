package proj.food.vista.implementation.swing.customer.components;

import proj.food.vista.implementation.swing.shared.StatusBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerPanel extends JPanel {

    private final CustomerTablePanel tablePanel = new CustomerTablePanel();
    private final StatusBar statusBar  = new StatusBar();

    private final JButton btnShowList = new JButton("Show Customer List");
    private final JButton btnInsert   = new JButton("Insert Customer");
    private final JButton btnUpdate   = new JButton("Update Customer");
    private final JButton btnDelete   = new JButton("Delete Customer");
    private final JButton btnExit     = new JButton("Exit");

    public CustomerPanel() {
        buildUI();
    }

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("=== CUSTOMER MENU ===", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));
        header.setBorder(BorderFactory.createEmptyBorder(12, 8, 6, 8));

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        actionsPanel.add(btnShowList);
        actionsPanel.add(btnInsert);
        actionsPanel.add(btnUpdate);
        actionsPanel.add(btnDelete);
        actionsPanel.add(btnExit);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(actionsPanel, BorderLayout.NORTH);
        bottomPanel.add(statusBar,    BorderLayout.SOUTH);

        add(header,      BorderLayout.NORTH);
        add(tablePanel,  BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // --- listeners ---
    public void addShowListListener(ActionListener l) { btnShowList.addActionListener(l); }
    public void addInsertListener(ActionListener l)   { btnInsert.addActionListener(l); }
    public void addUpdateListener(ActionListener l)   { btnUpdate.addActionListener(l); }
    public void addDeleteListener(ActionListener l)   { btnDelete.addActionListener(l); }
    public void addExitListener(ActionListener l)     { btnExit.addActionListener(l); }

    // --- tabla ---
    public CustomerTablePanel getTablePanel() { return tablePanel; }

    // --- status ---
    public void setStatus(String msg) { statusBar.setText(msg); }
}