package proj.food.vista.implementation.fx.start.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class StartPane extends VBox {

    public StartPane() {
        buildUI();
    }

    private void buildUI() {
        setAlignment(Pos.CENTER);
        setSpacing(20);

        // Use a container for the hero section
        VBox heroContainer = new VBox(15);
        heroContainer.setAlignment(Pos.CENTER);
        heroContainer.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 40; -fx-background-radius: 10;");

        Label title = new Label("Plataforma de Gestión de Comida");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label subtitle = new Label("Sistema de gestión de clientes y alimentos");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");

        heroContainer.getChildren().addAll(title, subtitle);

        Region topSpacer = new Region();
        Region bottomSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        getChildren().addAll(topSpacer, heroContainer, bottomSpacer);
        setStyle("-fx-background-color: white;");
    }
}

