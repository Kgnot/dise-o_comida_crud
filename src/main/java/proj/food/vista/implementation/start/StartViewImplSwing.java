package proj.food.vista.implementation.start;

import proj.food.controller.StartViewController;
import proj.food.vista.ViewType;
import proj.food.vista.interfaces.StartView;
import proj.food.vista.mediatr.MediatorView;

import javax.swing.*;
import java.awt.*;

public class StartViewImplSwing extends JFrame implements StartView {

    private StartViewController controller;
    private MediatorView mediator;

    public StartViewImplSwing() {
        buildUI();
    }

    private StartViewController getController() {
        if (controller == null) {
            controller = new StartViewController(this);
        }
        return controller;
    }

    private void buildUI() {
        setTitle("Start View");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(430, 260);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Bienvenidos a la plataforma de comida!", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 14f));
        header.setBorder(BorderFactory.createEmptyBorder(14, 8, 8, 8));

        JButton btnFood = new JButton("Ir a Food View");
        JButton btnCustomer = new JButton("Ir a Customer View");
        JButton btnExit = new JButton("Exit");

        btnFood.addActionListener(e -> getController().processMenuOption("1"));
        btnCustomer.addActionListener(e -> getController().processMenuOption("2"));
        btnExit.addActionListener(e -> getController().processMenuOption("3"));

        JPanel optionsPanel = new JPanel(new GridLayout(3, 1, 8, 8));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 16, 20));
        optionsPanel.add(btnFood);
        optionsPanel.add(btnCustomer);
        optionsPanel.add(btnExit);

        add(header, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
    }

    @Override
    public void showMenu() {
        setVisible(true);
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    @Override
    public void goToFoodView() {
        mediator.changeView(ViewType.FOOD);
    }

    @Override
    public void goToCustomerView() {
        mediator.changeView(ViewType.CUSTOMER);
    }

    @Override
    public void setMediator(MediatorView mv) {
        this.mediator = mv;
    }
}
