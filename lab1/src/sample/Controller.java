package sample;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.Pair;

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
    public void buildGraph() {
        System.out.println("pressedBuildButton");

        Dichotomy dichotomy = new Dichotomy();
        list = dichotomy.calculate();

        ObservableList<String> lines = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); ++i) {
            String str = String.valueOf(i + 1);
            lines.add(str);
        }
        comboLineBox.setItems(lines);

    }

    public void lineChosen() {
        int pos = Integer.parseInt(comboLineBox.getValue());
        Pair<Number, Number> pairChosen = list.get(pos - 1);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>(pairChosen.getKey(), pos));
        series.getData().add(new XYChart.Data<>(pairChosen.getValue(), pos));
        //series.setName(comboMethodsBox.getValue());
        series.setName(pairChosen.getKey() + " " + pairChosen.getValue());
        lineChart.getData().add(series);
    }

}
