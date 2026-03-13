package proj.food.view.implementation.fx;

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
import proj.food.view.ViewType;
import proj.food.view.implementation.fx.customer.CustomerViewFX;
import proj.food.view.implementation.fx.food.FoodViewFX;
import proj.food.view.implementation.fx.start.StartViewImplFX;
import proj.food.view.shell.Shell;

import java.util.EnumMap;
import java.util.Map;

public class MainShellFX implements Shell {

    private final Stage stage = new Stage();
    private final StackPane contentPane = new StackPane();
    private final Map<ViewType, Node> nodes = new EnumMap<>(ViewType.class);

    private final StartViewImplFX  startView    = new StartViewImplFX();
    private final FoodViewFX       foodView     = new FoodViewFX();
    private final CustomerViewFX   customerView = new CustomerViewFX();

    public static MainShellFX create() {
        return new MainShellFX();
    }

    public MainShellFX() {
        initViews();
        buildUI();
    }

    private void initViews() {
        nodes.put(ViewType.START,    startView.getPane());
        nodes.put(ViewType.FOOD,     foodView.getPane());
        nodes.put(ViewType.CUSTOMER, customerView.getPane());
    }

    private void buildUI() {
        BorderPane root = new BorderPane();
        root.setLeft(buildSidebar());
        root.setCenter(contentPane);

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

        Button btnStart    = buildNavButton("Inicio");
        Button btnFood     = buildNavButton("Comida");
        Button btnCustomer = buildNavButton("Clientes");
        Button btnExit     = buildNavButton("Salir");

        btnStart.setOnAction(e    -> navigate(ViewType.START));
        btnFood.setOnAction(e     -> navigate(ViewType.FOOD));
        btnCustomer.setOnAction(e -> navigate(ViewType.CUSTOMER));
        btnExit.setOnAction(e     -> System.exit(0));

        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(title, btnStart, btnFood, btnCustomer, spacer, btnExit);
        return sidebar;
    }

    private Button buildNavButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    @Override
    public void navigate(ViewType type) {
        Node node = nodes.get(type);
        if (node == null) throw new IllegalStateException("View not registered: " + type);
        contentPane.getChildren().setAll(node);
    }

    @Override
    public void launch(ViewType initial) {
        navigate(initial);
        stage.show();
        stage.toFront();
    }
}