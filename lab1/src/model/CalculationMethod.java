package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CalculationMethod {
    // Left border of the segment under study
    protected double left = 0.1;
    // Right border of the segment under study
    protected double right = 2.5;
    protected double epsilon = 0.0001;
    // List of points for building graph
    protected final List<Map<String, Double>> graphPoints = new ArrayList<>();

    // Abstract method for finding minimum
    public abstract double calculate();

    public double calculate(double eps) {
        this.epsilon = eps;
        return calculate();
    }

    // Method return function value in x
    protected double calculateFunctionValue(double x) {
        return Function.calculateFunctionValue(x);
    }

    // Method return true if function in x1 less than in x2
    protected boolean isFunctionLess(double x1, double x2) {
        return calculateFunctionValue(x1) < calculateFunctionValue(x2);
    }

    // Method return points for build graph
    public List<Map<String, Double>> getGraphPoints() {
        return graphPoints;
    }

}
