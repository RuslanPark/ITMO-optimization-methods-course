package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import model.*;

import java.util.List;
import java.util.Map;

public class Controller {

    @FXML
    private ComboBox<String> comboMethodsBox, comboLineBox;
    @FXML
    private LineChart<Double, Double> lineChart;

    public List<Map<String, Double>> list;
    CalculationMethod calculationMethod = null;
    double res = 0;

    @FXML
    public void initialize() {
        ObservableList<String> methods = FXCollections.observableArrayList("Dichotomy", "Golden ratio", "Fibonacci", "Parabolas", "Brent's");
        comboMethodsBox.setItems(methods);
    }

    public void methodChosen() {
        lineChart.getData().clear();
        comboLineBox.getSelectionModel().clearSelection();

        switch (comboMethodsBox.getValue()) {
            case ("Dichotomy") -> calculationMethod = new Dichotomy();
            case ("Golden ratio") -> calculationMethod = new GoldenRatio();
            case ("Fibonacci") -> calculationMethod = new Fibonacci();
            case ("Parabolas") -> calculationMethod = new Parabolas();
            default -> calculationMethod = new Brents();
        }

        OptionsMenu menu = new OptionsMenu();
        menu.showMenu();
        buildGraph(menu.epsilon);
    }

    public void buildGraph(double epsilon) {
        // Create alert dialog for change notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        try {

            // Calculate plot points by chosen method
            res = calculationMethod.calculate(epsilon);
            list = calculationMethod.getGraphPoints();

            // Collect points and iterations count
            ObservableList<String> lines = FXCollections.observableArrayList();
            for (int i = 0; i < list.size(); ++i) {
                String str = String.valueOf(i + 1);
                lines.add(str);
            }
            comboLineBox.setItems(lines);


            alert.setTitle("Success!");
            alert.setContentText("Graphic has benn build, chose step in the next comboBox!");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());

            alert.setTitle("Error!");
            alert.setContentText("Chose calculation method before you build the graphic!");
            alert.showAndWait();
        }
    }

    public void lineChosen() {
        if (comboLineBox.getValue() == null) {
            return;
        }
        int chosenLinePosition = Integer.parseInt(comboLineBox.getValue());
        Map<String, Double> chosenPoint = list.get(chosenLinePosition - 1);

        // Clear chart
        lineChart.getData().clear();
        lineChart.setCreateSymbols(false);

        double a, b;
        a = chosenPoint.get("left");
        b = chosenPoint.get("right");

        if (calculationMethod instanceof Parabolas) {
            XYChart.Series<Double, Double> parabola = new XYChart.Series<>();
            double delta = (b - a) / 100;
            while (a < b) {
                parabola.getData().add(new XYChart.Data<>(a,
                        chosenPoint.get("a2") * (a - chosenPoint.get("left")) * (a - chosenPoint.get("x2"))
                                + chosenPoint.get("a1") * (a - chosenPoint.get("left")) + chosenPoint.get("a0")));
                a += delta;
            }

            a = chosenPoint.get("left");

            // Add points (XYChart.Data) in special container (XYChart.Series)
            XYChart.Series<Double, Double> series = paintGraph(a, b);
            // Set legend format and load container with points
            series.setName("Left X= " + chosenPoint.get("left") + "  |  Right X= " + chosenPoint.get("right") + " result = " + res);
            lineChart.getData().add(series);

            lineChart.getData().add(parabola);
            parabola.setName("Approximating parabola");
            parabola.getNode().setStyle("-fx-stroke-width: 2; -fx-stroke-line-cap: round; -fx-stroke-dash-array: 4;");

        } else {
            a = chosenPoint.get("left");
            b = chosenPoint.get("right");

            // Add points (XYChart.Data) in special container (XYChart.Series)
            XYChart.Series<Double, Double> series = paintGraph(a, b);
            series.setName("Left X= " + chosenPoint.get("left") + "  |  Right X= " + chosenPoint.get("right") + " result = " + res);
            lineChart.getData().add(series);

            // Add point X1 (XYChart.Data) in special container (XYChart.Series)
            XYChart.Series<Double, Double> x1Point = new XYChart.Series<>();
            x1Point.getData().add(new XYChart.Data<>(chosenPoint.get("x1"), Function.calculateFunctionValue(chosenPoint.get("x1"))));
            x1Point.setName("x1 is " + chosenPoint.get("x1"));
            // Add point X2 (XYChart.Data) in special container (XYChart.Series)
            XYChart.Series<Double, Double> x2Point = new XYChart.Series<>();
            x2Point.getData().add(new XYChart.Data<>(chosenPoint.get("x2"), Function.calculateFunctionValue(chosenPoint.get("x2"))));
            x2Point.setName("x2 is " + chosenPoint.get("x2"));

            lineChart.getData().add(x1Point);
            lineChart.getData().add(x2Point);

            x1Point.getNode().setStyle("-fx-stroke-width: 7; -fx-stroke-line-cap: round;");
            x2Point.getNode().setStyle("-fx-stroke-width: 7; -fx-stroke-line-cap: round;");
        }

    }

    private XYChart.Series<Double, Double> paintGraph(double a, double b) {
        XYChart.Series<Double, Double> graph = new XYChart.Series<>();
        double delta = (b - a) / 100;
        while (a < b) {
            double result = Function.calculateFunctionValue(a);
            a += delta;
            graph.getData().add(new XYChart.Data<>(a, result));
        }

        return graph;
    }
}