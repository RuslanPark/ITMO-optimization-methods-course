package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class CalculationMethod {
    // Left border of the segment under study
    protected double left = 0.1;
    // Right border of the segment under study
    protected double right = 2.5;
    // List of points for building graph
    protected final List<Pair<Double, Double>> graphPoints = new ArrayList<>();

    // Abstract method fod finding minimum
    public abstract double calculate();

    // Method return function value in x
    protected double calculateFunctionValue(double x) {
        return 10 * x * Math.log(x) - x * x / 2;
    }

    // Method return true if function in x1 less than in x2
    protected boolean isFunctionLess(double x1, double x2) {
        return calculateFunctionValue(x1) < calculateFunctionValue(x2);
    }

    // Method return points for build graph
    public List<Pair<Double, Double>> getGraphPoints() {
        return graphPoints;
    }

}
