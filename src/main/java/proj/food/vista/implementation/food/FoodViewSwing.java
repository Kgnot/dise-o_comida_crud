package proj.food.vista.implementation.food;

import proj.food.controller.FoodViewController;
import proj.food.vista.interfaces.FoodView;
import proj.food.vista.mediatr.MediatorView;

import javax.swing.*;
import java.awt.*;

public class FoodViewSwing extends JFrame implements FoodView {

    private FoodViewController controller;
    private MediatorView mediator;
    private final JLabel statusLabel = new JLabel(" ");

    public FoodViewSwing() {
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
        setSize(420, 260);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("=== FOOD MENU ===", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));
        header.setBorder(BorderFactory.createEmptyBorder(12, 8, 6, 8));

        JButton btnShowList = new JButton("Show Food List");
        JButton btnExit = new JButton("Exit");

        btnShowList.addActionListener(e -> getController().processMenuOption("1"));
        btnExit.addActionListener(e -> getController().processMenuOption("2"));

        JPanel actionsPanel = new JPanel(new GridLayout(2, 1, 8, 8));
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 8, 20));
        actionsPanel.add(btnShowList);
        actionsPanel.add(btnExit);

        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 12, 8, 12));

        add(header, BorderLayout.NORTH);
        add(actionsPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    @Override
    public void showMenu() {
        setVisible(true);
    }

    @Override
    public void exit() {
        statusLabel.setText("Exiting Food View...");
        dispose();
    }

    @Override
    public void setMediator(MediatorView mediator) {
        this.mediator = mediator;
    }
}

