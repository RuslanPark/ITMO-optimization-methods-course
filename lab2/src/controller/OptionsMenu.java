package controller;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionsMenu {

    public static double epsilon = 0;
    public static double x = 1;
    public static double y = 1;

    OptionsMenu() {

    }

    public static void showMenu() {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Write parameters");
        dialog.setHeaderText("Write parameters for method\nSkip to use default eps = 0.0001, x = 1.0, y = 1.0");

        // Set the button types.
        ButtonType confirmButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        // Create the epsilon label and field.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField epsilonTextField = new TextField();
        epsilonTextField.setPromptText("epsilon");

        TextField xTextField = new TextField();
        xTextField.setPromptText("X");
        TextField yTextField = new TextField();
        yTextField.setPromptText("Y");

        grid.add(new Label("Epsilon:"), 0, 0);
        grid.add(epsilonTextField, 1, 0);

        grid.add(new Label("Start X:"), 0, 1);
        grid.add(xTextField, 1, 1);

        grid.add(new Label("Start Y:"), 0, 2);
        grid.add(yTextField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(epsilonTextField::requestFocus);

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                if (epsilonTextField.getText().isEmpty() || !epsilonTextField.getText().matches("0.\\d+")) {
                    return "0.0001";
                }
                return epsilonTextField.getText();
            }
            return "0.0001";
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(it -> {
            epsilon = Double.parseDouble(it);
            if (xTextField.getText().isEmpty()) {
                x = 1.0;
            } else {
                x = Double.parseDouble(xTextField.getText());
            }
            if (yTextField.getText().isEmpty()) {
                y = 1.0;
            } else {
                y = Double.parseDouble(yTextField.getText());
            }
        });
    }
}
