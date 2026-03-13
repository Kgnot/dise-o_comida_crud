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
        setSpacing(10);

        Label title = new Label("Plataforma de Gestión de Comida");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label subtitle = new Label("Sistema de gestión de clientes y alimentos");

        Region topSpacer = new Region();
        Region bottomSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        getChildren().addAll(topSpacer, title, subtitle, bottomSpacer);
    }
}

