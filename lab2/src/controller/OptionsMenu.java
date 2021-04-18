package controller;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class OptionsMenu {

    public static double epsilon = 0;

    OptionsMenu() {

    }

    public static void showMenu() {
        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Write parameters");
        dialog.setHeaderText("Write parameters for method\nSkip to use default eps = 0.0001");

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

        grid.add(new Label("Epsilon:"), 0, 0);
        grid.add(epsilonTextField, 1, 0);

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
        });
    }
}
