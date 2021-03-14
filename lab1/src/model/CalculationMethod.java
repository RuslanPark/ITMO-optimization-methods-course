package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Abstract class for methods
public abstract class CalculationMethod {
    // Left border of the segment under study
    protected double left = Function.getLeft();
    // Right border of the segment under study
    protected double right = Function.getRight();
    // Default epsilon
    protected double epsilon = 0.0001;
    // List of points for building graph
    protected final List<Map<String, Double>> graphPoints = new ArrayList<>();

    // Abstract method for finding minimum
    public abstract double calculate();

    // Method for finding minimum with the specified epsilon value
    public double calculate(double eps) {
        this.epsilon = eps;
        return calculate();
    }

    // Method return points for build graph
    public List<Map<String, Double>> getGraphPoints() {
        return graphPoints;
    }

}
