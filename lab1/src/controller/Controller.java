package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.Pair;
import model.*;
import java.util.List;

public class Controller {

    public ComboBox<String> comboMethodsBox;
    public ComboBox<String> comboLineBox;
    public Button buildButton;
    public LineChart<Double, Double> lineChart;

    public List< Pair<Double, Double> > list;
    CalculationMethod calculationMethod = null;


    public void initialize() {
        ObservableList<String> methods = FXCollections.observableArrayList("Dichotomy", "Golden ratio", "Fibonacci", "Parabolas","Brent's");
        comboMethodsBox.setItems(methods);

    }

    public void methodChosen() {
        switch (comboMethodsBox.getValue()) {
            case ("Dichotomy") -> calculationMethod = new Dichotomy();
            case ("Golden ratio") -> calculationMethod = new GoldenRatio();
            case ("Fibonacci") -> calculationMethod = new Fibonacci();
            case ("Parabolas") -> calculationMethod = new Parabolas();
            default -> calculationMethod = new Brents();
        }
    }

    public void buildGraph() {
        // Create alert dialog for change notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        try {
            // Calculate plot points by chosen method
            double res = calculationMethod.calculate();
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
        int chosenLinePosition = Integer.parseInt(comboLineBox.getValue());
        Pair<Double, Double> chosenPoint = list.get(chosenLinePosition - 1);
        double a = chosenPoint.getKey();
        double b = chosenPoint.getValue();

        // Clear chart
        lineChart.getData().clear();

        // Add points (XYChart.Data) in special container (XYChart.Series)
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        while (a < b) {
            double result = 10 * a * Math.log(a) + a * a / 2;
            a += 0.01;
            series.getData().add(new XYChart.Data<>(a, result));
        }
        // Set legend format and load container with points
        series.setName("Left X= " + chosenPoint.getKey() + "  |  Right X= " + chosenPoint.getValue());
        lineChart.getData().add(series);
    }

}