package proj.food.view.implementation.fx.shared;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StatusBarFX extends HBox {

    private final Label label = new Label(" ");

    public StatusBarFX() {
        getStyleClass().add("status-bar");
        getChildren().add(label);
    }

    public void setText(String message) {
        label.setText(message);
    }

    public void clear() {
        label.setText(" ");
    }
}

