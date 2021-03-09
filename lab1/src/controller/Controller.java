package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.util.Pair;
import model.*;

import java.util.List;

public class Controller {

    @FXML
    private ComboBox<String> comboMethodsBox, comboLineBox;
    @FXML
    private LineChart<Double, Double> lineChart;

    public List< Pair<Double, Double> > list;
    CalculationMethod calculationMethod = null;
    double res = 0;

    @FXML
    public void initialize() {
        ObservableList<String> methods = FXCollections.observableArrayList("Dichotomy", "Golden ratio", "Fibonacci", "Parabolas","Brent's");
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

        buildGraph();
    }

    public void buildGraph() {
        // Create alert dialog for change notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        try {

            // Calculate plot points by chosen method
            res = calculationMethod.calculate();
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
        Pair<Double, Double> chosenPoint = list.get(chosenLinePosition - 1);
        double a = chosenPoint.getKey();
        double b = chosenPoint.getValue();

        // Clear chart
        lineChart.getData().clear();
        lineChart.setCreateSymbols(false);

        // Add points (XYChart.Data) in special container (XYChart.Series)
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        double delta = (b - a) / 100;
        while (a < b) {
            double result = 10 * a * Math.log(a) - a * a / 2;
            a += delta;
            series.getData().add(new XYChart.Data<>(a, result));
        }
        // Set legend format and load container with points
        series.setName("Left X= " + chosenPoint.getKey() + "  |  Right X= " + chosenPoint.getValue());

        XYChart.Series<Double, Double> seriesPoint = new XYChart.Series<>();
        seriesPoint.getData().add(new XYChart.Data<>(res, 10 * res * Math.log(res) - res * res / 2));
        seriesPoint.setName("Answer point is " + res);

        lineChart.getData().add(series);
        lineChart.getData().add(seriesPoint);

        seriesPoint.getNode().setStyle("-fx-stroke-width: 7;");
    }
}