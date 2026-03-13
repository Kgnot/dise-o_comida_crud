package proj.food.vista.implementation.swing;

import proj.food.vista.ViewType;
import proj.food.vista.implementation.swing.customer.CustomerViewSwing;
import proj.food.vista.implementation.swing.food.FoodViewSwing;
import proj.food.vista.implementation.swing.start.StartViewSwing;
import proj.food.vista.mediatr.MediatorView;
import proj.food.vista.mediatr.MediatorViewShell;
import proj.food.vista.shell.Shell;

import javax.swing.*;
import java.awt.*;

public class MainShellSwing extends JFrame implements Shell {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    // Views
    private final StartViewSwing startView = new StartViewSwing();
    private final FoodViewSwing foodView = new FoodViewSwing();
    private final CustomerViewSwing customerView = new CustomerViewSwing();

    public static MainShellSwing create() {
        MainShellSwing shell = new MainShellSwing();
        shell.wireMediator();
        return shell;
    }

    public MainShellSwing() {
        buildUI();
    }

    private void buildUI() {
        setTitle("Plataforma de Comida");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        contentPanel.add(startView.getPanel(), ViewType.START.name());
        contentPanel.add(foodView.getPanel(), ViewType.FOOD.name());
        contentPanel.add(customerView.getPanel(), ViewType.CUSTOMER.name());

        add(buildSidebar(), BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel buildSidebar() {

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(200, 0));

        sidebar.setBorder(BorderFactory.createMatteBorder(
                0, 0, 0, 1,
                UIManager.getColor("Separator.foreground")
        ));

        // ---- HEADER ----
        JLabel title = new JLabel("Plataforma de Comida");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));
        title.setBorder(BorderFactory.createEmptyBorder(20,16,20,16));

        // ---- NAVIGATION ----
        JButton btnStart = buildNavButton("Inicio");
        JButton btnFood = buildNavButton("Comida");
        JButton btnCustomer = buildNavButton("Clientes");

        btnStart.addActionListener(e -> showView(ViewType.START));
        btnFood.addActionListener(e -> showView(ViewType.FOOD));
        btnCustomer.addActionListener(e -> showView(ViewType.CUSTOMER));

        JPanel nav = new JPanel();
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));

        nav.add(btnStart);
        nav.add(Box.createVerticalStrut(6));
        nav.add(btnFood);
        nav.add(Box.createVerticalStrut(6));
        nav.add(btnCustomer);

        // ---- EXIT ----
        JButton btnExit = buildNavButton("Salir");
        btnExit.addActionListener(e -> System.exit(0));

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(10,10,16,10));
        bottom.add(btnExit, BorderLayout.SOUTH);

        sidebar.add(title, BorderLayout.NORTH);
        sidebar.add(nav, BorderLayout.CENTER);
        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

    private JButton buildNavButton(String text) {

        JButton btn = new JButton(text);

        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.setBorder(BorderFactory.createEmptyBorder(10,12,10,12));

        btn.putClientProperty("JButton.buttonType", "toolBarButton");

        return btn;
    }

    private void wireMediator() {
        MediatorView mediator = new MediatorViewShell(this);

        mediator.addView(ViewType.START, startView);
        mediator.addView(ViewType.FOOD, foodView);
        mediator.addView(ViewType.CUSTOMER, customerView);
    }

    public void showView(ViewType type) {
        cardLayout.show(contentPanel, type.name());
    }

    public void show(ViewType initial) {
        showView(initial);
        setVisible(true);
    }
}