package controllers;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.Pair;
import model.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Controller {

    @FXML
    public ComboBox<String> comboMethodsBox;
    @FXML
    public ComboBox<String> comboLineBox;
    @FXML
    public Button buildButton;
    @FXML
    public LineChart<Number, Number> lineChart;

    public void initialize() {
        ObservableList<String> methods = FXCollections.observableArrayList("Dichotomy", "Golden ratio", "Fibonacci", "Parabolas","Brent's");
        comboMethodsBox.setItems(methods);

    }

    public List< Pair<Number, Number> > list;
    CalculationMethod calculationMethod = null;

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
        try {
            list = calculationMethod.calculate();
            ObservableList<String> lines = FXCollections.observableArrayList();
            for (int i = 0; i < list.size(); ++i) {
                String str = String.valueOf(i + 1);
                lines.add(str);
            }
            comboLineBox.setItems(lines);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText(null);
            alert.setContentText("Graphic has benn build, chose step in the next comboBox!");
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Chose calculation method before you build the graphic!");
            alert.showAndWait();
        }
    }

    public void lineChosen() {
        int pos = Integer.parseInt(comboLineBox.getValue());
        Pair<Number, Number> pairChosen = list.get(pos - 1);
        float a = (float) pairChosen.getKey();
        float b = (float) pairChosen.getValue();
        lineChart.getData().clear();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        while (a < b) {
            float result = (float) (a * a + Math.exp(-0.35 * a));
            a += 0.01;
            series.getData().add(new XYChart.Data<>(a, result));
        }
        series.setName("X= " + pairChosen.getKey() + " f(X)= " + pairChosen.getValue());
        lineChart.getData().add(series);
    }

}
