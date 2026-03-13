package proj.food.vista.implementation.fx;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proj.food.vista.ViewType;
import proj.food.vista.implementation.fx.customer.CustomerViewFX;
import proj.food.vista.implementation.fx.food.FoodViewFX;
import proj.food.vista.implementation.fx.start.StartViewImplFX;
import proj.food.vista.mediatr.MediatorView;
import proj.food.vista.mediatr.MediatorViewShell;
import proj.food.vista.shell.Shell;

import java.util.EnumMap;
import java.util.Map;

public class MainShellFX implements Shell {

    private final Stage stage = new Stage();
    private final StackPane contentPane = new StackPane();
    // Keep one node per screen and swap them in the center container.
    private final Map<ViewType, Node> views = new EnumMap<>(ViewType.class);

    private final StartViewImplFX startView = new StartViewImplFX();
    private final FoodViewFX foodView = new FoodViewFX();
    private final CustomerViewFX customerView = new CustomerViewFX();

    private MediatorView mediator;

    public static MainShellFX create() {
        MainShellFX shell = new MainShellFX();
        shell.wireMediator();
        return shell;
    }

    public MainShellFX() {
        buildUI();
    }

    private void buildUI() {
        BorderPane root = new BorderPane();
        root.setLeft(buildSidebar());
        root.setCenter(contentPane);

        views.put(ViewType.START, startView.getPane());
        views.put(ViewType.FOOD, foodView.getPane());
        views.put(ViewType.CUSTOMER, customerView.getPane());

        stage.setTitle("Plataforma de Comida");

        Scene scene = new Scene(root, 900, 520);

        scene.getStylesheets().add(
                getClass().getResource("/styles.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setMaximized(true);
    }

    private VBox buildSidebar() {
        VBox sidebar = new VBox(8);
        sidebar.setPrefWidth(200);
        sidebar.setPadding(new Insets(20, 10, 16, 10));
        sidebar.setStyle("-fx-border-color: #d0d0d0; -fx-border-width: 0 1 0 0;");

        Label title = new Label("Plataforma de Comida");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button btnStart = buildNavButton("Inicio");
        Button btnFood = buildNavButton("Comida");
        Button btnCustomer = buildNavButton("Clientes");
        Button btnExit = buildNavButton("Salir");

        btnStart.setOnAction(e -> showView(ViewType.START));
        btnFood.setOnAction(e -> showView(ViewType.FOOD));
        btnCustomer.setOnAction(e -> showView(ViewType.CUSTOMER));
        btnExit.setOnAction(e -> {
            // Delegate app shutdown when a mediator is available.
            if (mediator != null) {
                mediator.exitApplication();
            } else {
                System.exit(0);
            }
        });

        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(title, btnStart, btnFood, btnCustomer, spacer, btnExit);
        return sidebar;
    }

    private Button buildNavButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        // Styles applied via CSS
        return button;
    }

    private void wireMediator() {
        // Wire all screens once so each view can navigate through the mediator.
        mediator = new MediatorViewShell(this);
        mediator.addView(ViewType.START, startView);
        mediator.addView(ViewType.FOOD, foodView);
        mediator.addView(ViewType.CUSTOMER, customerView);
    }

    public void showView(ViewType type) {
        Node node = views.get(type);
        if (node == null) {
            throw new IllegalStateException("View not registered in shell: " + type);
        }
        // Replace the current content with the selected view.
        contentPane.getChildren().setAll(node);
    }

    public void show(ViewType initial) {
        showView(initial);
        stage.show();
        stage.toFront();
    }
}
