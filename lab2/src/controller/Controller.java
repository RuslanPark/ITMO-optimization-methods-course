package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.ScrollEvent;
import javafx.util.Pair;
import methods.*;

import java.util.*;

import static java.lang.Math.sqrt;

public class Controller {

    @FXML
    private ComboBox<String> comboFunctionsBox, comboMethodsBox, comboLineBox;
    @FXML
    private LineChart<Number, Number> lineChart;

    AbstractMethod method = null;
    int total;
    double zoom = 1.0;

    @FXML
    public void initialize() {
        final NumberAxis axisX = (NumberAxis) lineChart.getXAxis();
        final double lowerX = axisX.getLowerBound();
        final double upperX = axisX.getUpperBound();
        final NumberAxis axisY = (NumberAxis) lineChart.getYAxis();
        final double lowerY = axisY.getLowerBound();
        final double upperY = axisY.getUpperBound();

        lineChart.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                final double minX = axisX.getLowerBound();
                final double maxX = axisX.getUpperBound();
                final double minY = axisY.getLowerBound();
                final double maxY = axisY.getUpperBound();

                double thresholdX = minX + (maxX - minX) / 2d;
                double x = event.getX();
                double valueX = axisX.getValueForDisplay(x).doubleValue();

                double thresholdY = minY + (maxY - minY) / 2d;
                double y = event.getY();
                double valueY = axisY.getValueForDisplay(y).doubleValue();

                double direction = event.getDeltaY();
                if (direction > 0) {
                    if (maxX - minX > zoom) {
                        if (valueX > thresholdX) {
                            axisX.setLowerBound(minX + zoom);
                        } else {
                            axisX.setUpperBound(maxX - zoom);
                        }
                    }
                    if (maxY - minY > zoom) {
                        if (valueY > thresholdY) {
                            axisY.setLowerBound(minY + zoom);
                        } else {
                            axisY.setUpperBound(maxY - zoom);
                        }
                    }
                    if (maxX - minX < 10 * zoom) {
                        zoom /= 10;
                    }
                    if (maxY - minY < 10 * zoom) {
                        zoom /= 10;
                    }
                } else {
                    if (maxX - minX > 10 * zoom) {
                        zoom *= 10;
                    }
                    if (maxY - minY > 10 * zoom) {
                        zoom *= 10;
                    }
                    if (valueX < thresholdX) {
                        double nextBound = Math.max(lowerX, minX - zoom);
                        axisX.setLowerBound(nextBound);
                    } else {
                        double nextBound = Math.min(upperX, maxX + zoom);
                        axisX.setUpperBound(nextBound);
                    }
                    if (valueY < thresholdY) {
                        double nextBound = Math.max(lowerY, minY - zoom);
                        axisY.setLowerBound(nextBound);
                    } else {
                        double nextBound = Math.min(upperY, maxY + zoom);
                        axisY.setUpperBound(nextBound);
                    }
                }

            }
        });

        ObservableList<String> functions = FXCollections.observableArrayList("F1", "F2", "F3");
        comboFunctionsBox.setItems(functions);
        ObservableList<String> methods = FXCollections.observableArrayList("GradientDescent", "SteepestDescent", "ConjugateGradients");
        comboMethodsBox.setItems(methods);
    }

    public void functionChosen() {
        lineChart.getData().clear();
        comboLineBox.getSelectionModel().clearSelection();
    }

    public void methodChosen() {
        lineChart.getData().clear();
        comboLineBox.getSelectionModel().clearSelection();

        for (int j = -200; j < 100; j += 10) {
            XYChart.Series<Number, Number> graph = new XYChart.Series<>();
            ArrayList<Pair<Double, Double>> arrayList1 = new ArrayList<>();
            ArrayList<Pair<Double, Double>> arrayList2 = new ArrayList<>();
            for (double i = -30.0; i < 30.0; i += 0.1) {
                double z = j;
                double y = i;
                double sq = Function.funcOneSqrt(y, z);
                if (sq >= 0) {
                    double x1 = Function.funcOneXFirst(y, z);
                    double x2 = Function.funcOneXSecond(y, z);
                    //System.out.println(y + " " + x1 + " " + x2);
                    arrayList1.add(new Pair<>(x1, y));
                    arrayList2.add(new Pair<>(x2, y));
                }
            }
            for (Pair<Double, Double> i : arrayList1) {
                graph.getData().add(new XYChart.Data<>(i.getKey(), i.getValue()));
            }
            for (int i = arrayList2.size() - 1; i > 0; --i) {
                Pair<Double, Double> p = arrayList2.get(i);
                graph.getData().add(new XYChart.Data<>(p.getKey(), p.getValue()));
            }
            lineChart.getData().add(graph);
            graph.getNode().setStyle("-fx-stroke-width: 2; -fx-stroke-line-cap: round; -fx-stroke-dash-array: 4;");
        }
        total = lineChart.getData().size();

        switch (comboMethodsBox.getValue()) {
            case ("GradientDescent") -> method = new GradientDescent();
            case ("SteepestDescent") -> method = new SteepestDescent();
            default -> method = new ConjugateGradients();
        }

        OptionsMenu.showMenu();
        buildGraph(OptionsMenu.epsilon);
    }

    public void buildGraph(double epsilon) {

        List<List<Double>> matrix = Function.funcOneMatrix;
        List<Double> startPoint = List.of(1.0, 1.0);

        Function function = new Function(matrix);
        method.calculate(startPoint, epsilon, function);

        // Create alert dialog for change notification
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        try {
            // Collect points and iterations count
            ObservableList<String> lines = FXCollections.observableArrayList();
            for (int i = 0; i < method.getPoints().size(); ++i) {
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
        lineChart.getData().remove(total - 1, lineChart.getData().size());

        int cnt = comboLineBox.getSelectionModel().getSelectedIndex() + 1;
        XYChart.Series<Number, Number> graph = new XYChart.Series<>();
        for (int i = 0; i < cnt; ++i) {
            List<Double> j = method.getPoints().get(i);
            graph.getData().add(new XYChart.Data<>(j.get(0), j.get(1)));
        }
        graph.setName(null);
        lineChart.getData().add(graph);
    }
}