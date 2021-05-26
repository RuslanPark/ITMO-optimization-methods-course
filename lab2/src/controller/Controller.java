package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.util.Pair;
import methods.*;

import java.util.*;

public class Controller {

    @FXML
    private RadioButton lines, axis;
    @FXML
    private ComboBox<String> comboFunctionsBox, comboMethodsBox, comboLineBox;
    @FXML
    private LineChart<Number, Number> lineChart;

    private String func = null;
    private AbstractMethod method = null;
    int total;
    double zoom = 1.0;
    double startX;
    double startY;

    @FXML
    public void initialize() {
        lineChart.setLegendVisible(false);
        final NumberAxis axisX = (NumberAxis) lineChart.getXAxis();
        final double lowerX = axisX.getLowerBound();
        final double upperX = axisX.getUpperBound();
        final NumberAxis axisY = (NumberAxis) lineChart.getYAxis();
        final double lowerY = axisY.getLowerBound();
        final double upperY = axisY.getUpperBound();

        lines.setOnAction(event -> {
            if (!lineChart.getData().isEmpty() && func != null) {
                if (lines.isSelected()) {
                    paintLine(func);
                    if (comboLineBox.getSelectionModel().getSelectedIndex() != 0) {
                        lineChosen();
                    }
                } else {
                    deleteLine();
                }
            }
        });

        axis.setOnAction(event -> {
            if (!lineChart.getData().isEmpty()) {
                if (axis.isSelected()) {
                    lineChart.getXAxis().setTickLabelsVisible(true);
                    lineChart.getYAxis().setTickLabelsVisible(true);
                    lineChart.getXAxis().setTickMarkVisible(true);
                    lineChart.getYAxis().setTickMarkVisible(true);
                    lineChart.getXAxis().setLabel("x");
                    lineChart.getYAxis().setLabel("y");
                } else {
                    lineChart.getXAxis().setTickLabelsVisible(false);
                    lineChart.getYAxis().setTickLabelsVisible(false);
                    lineChart.getXAxis().setTickMarkVisible(false);
                    lineChart.getYAxis().setTickMarkVisible(false);
                    lineChart.getXAxis().setLabel("");
                    lineChart.getYAxis().setLabel("");
                }
            }
        });


        lineChart.setOnMouseDragged(event -> {
            if (event.isDragDetect()) {
                startX = event.getX();
                startY = event.getY();
            }

            double valueX = event.getX();
            double valueY = event.getY();

            double newX = startX - valueX;
            double newY = startY - valueY;

            System.out.println(newX + " " + newY);

            startX = valueX;
            startY = valueY;

            double dX;
            double dY;
            if (newX < 0) {
                dX = -zoom * 0.1;
            } else {
                dX = zoom * 0.1;
            }
            if (newY < 0) {
                dY = zoom * 0.1;
            } else {
                dY = -zoom * 0.1;
            }

            if (newX != 0) {
                axisX.setLowerBound(axisX.getLowerBound() + dX);
                axisX.setUpperBound(axisX.getUpperBound() + dX);
            }
            if (newY != 0) {
                axisY.setLowerBound(axisY.getLowerBound() + dY);
                axisY.setUpperBound(axisY.getUpperBound() + dY);
            }
        });

        lineChart.setOnScroll(event -> {
            final double minX = axisX.getLowerBound();
            final double maxX = axisX.getUpperBound();
            final double minY = axisY.getLowerBound();
            final double maxY = axisY.getUpperBound();
            double direction = event.getDeltaY();

            if (direction > 0) {

                axisX.setLowerBound(minX + zoom * 2);
                axisX.setUpperBound(maxX - zoom * 2);

                axisY.setLowerBound(minY + zoom);
                axisY.setUpperBound(maxY - zoom);

                if (maxX - minX < 10 * zoom) {
                    zoom /= 10;
                } else {
                    if (maxY - minY < 10 * zoom) {
                        zoom /= 10;
                    }
                }
            } else {
                if (maxX - minX > 10 * zoom) {
                    zoom = Math.min(zoom * 10, 1);
                } else {
                    if (maxY - minY > 10 * zoom) {
                        zoom = Math.min(zoom * 10, 1);
                    }
                }

                axisX.setLowerBound(Math.max(lowerX, minX - zoom * 2));
                axisX.setUpperBound(Math.min(upperX, maxX + zoom * 2));

                axisY.setLowerBound(Math.max(lowerY, minY - zoom));
                axisY.setUpperBound(Math.min(upperY, maxY + zoom));
            }

        });

        ObservableList<String> functions = FXCollections.observableArrayList("f(x, y) = x^2 + y^2 + 130", "f(x, y) = 64x^2 + 126xy + 64y^2 − 10x + 30y + 13");
        comboFunctionsBox.setItems(functions);
        ObservableList<String> methods = FXCollections.observableArrayList("GradientDescent", "SteepestDescent", "ConjugateGradients");
        comboMethodsBox.setItems(methods);
    }

    public void functionChosen() {
        lineChart.getData().clear();
        comboLineBox.getSelectionModel().clearSelection();
        lines.setSelected(false);
        axis.setSelected(false);
        if (comboFunctionsBox.getValue().equals("f(x, y) = x^2 + y^2 + 130")) {
            func = "first";
        } else {
            func = "second";
        }
    }

    public void methodChosen() {
        lineChart.getData().clear();
        comboLineBox.getSelectionModel().clearSelection();
        lines.setSelected(false);
        axis.setSelected(false);

        if (func == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Error!");
            alert.setContentText("Chose a function before you can chose calculation method!");
            alert.showAndWait();
            return;
        }

        paintLine(func);
        lines.setSelected(true);
        axis.setSelected(true);

        switch (comboMethodsBox.getValue()) {
            case ("GradientDescent") -> method = new GradientDescent();
            case ("SteepestDescent") -> method = new SteepestDescent();
            default -> method = new ConjugateGradients();
        }

        OptionsMenu.showMenu();
        buildGraph(OptionsMenu.epsilon, OptionsMenu.x, OptionsMenu.y);
    }

    public void buildGraph(double epsilon, double x, double y) {

        List<List<Double>> matrix = Function.funcMatrix(func);
        List<Double> startPoint = List.of(x, y);

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
        lineChart.getData().remove(total, lineChart.getData().size());

        int cnt = comboLineBox.getSelectionModel().getSelectedIndex() + 1;
        XYChart.Series<Number, Number> graph = new XYChart.Series<>();
        for (int i = 0; i < cnt; ++i) {
            List<Double> j = method.getPoints().get(i);
            graph.getData().add(new XYChart.Data<>(j.get(0), j.get(1)));
        }
        graph.setName(null);
        lineChart.getData().add(graph);
    }

    public void paintLine(String func) {
        int dec = 10;
        if (func.equals("second")) {
            dec += 20;
        }
        for (int j = -200; j < 200; j += dec) {
            XYChart.Series<Number, Number> graph = new XYChart.Series<>();
            ArrayList<Pair<Double, Double>> arrayList1 = new ArrayList<>();
            ArrayList<Pair<Double, Double>> arrayList2 = new ArrayList<>();
            for (double i = -30.0; i < 30.0; i += 0.1) {
                double z = j;
                double y = i;
                double sq = Function.funcSqrt(func, y, z);
                if (sq >= 0) {
                    double x1 = Function.funcXFirst(func, y, z);
                    double x2 = Function.funcXSecond(func, y, z);
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
            for (Pair<Double, Double> i : arrayList1) {
                graph.getData().add(new XYChart.Data<>(i.getKey(), i.getValue()));
                break;
            }
            lineChart.getData().add(0, graph);
            graph.getNode().setStyle("-fx-stroke-width: 2; -fx-stroke-line-cap: round; -fx-stroke-dash-array: 4;");
        }
        total = lineChart.getData().size();
    }

    public void deleteLine() {
        if (total > 0) {
            lineChart.getData().subList(0, total - 1).clear();
            total = 0;
        }
    }
}