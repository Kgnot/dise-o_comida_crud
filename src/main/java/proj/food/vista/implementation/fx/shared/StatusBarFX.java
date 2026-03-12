package proj.food.vista.implementation.fx.shared;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StatusBarFX extends HBox {

    private final Label label = new Label(" ");

    public StatusBarFX() {
        setPadding(new Insets(0, 12, 8, 12));
        getChildren().add(label);
    }

    public void setText(String message) {
        label.setText(message);
    }

    public void clear() {
        label.setText(" ");
    }
}

