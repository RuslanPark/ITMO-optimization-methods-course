package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class CalculationMethod {
    protected double left = 0.1;
    protected double right = 2.5;
    protected final List<Pair<Double, Double>> graphPoints = new ArrayList<>();

    public abstract double calculate();

    protected double calculateFunctionValue(double x) {
        return 10 * x * Math.log(x) - x * x / 2;
    }

    public List<Pair<Double, Double>> getGraphPoints() {
        return graphPoints;
    }

}
