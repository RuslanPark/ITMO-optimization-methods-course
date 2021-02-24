package model;

import javafx.util.Pair;

public class Dichotomy extends CalculationMethod {

    double delta = 0.0001;
    double epsilon = 0.0001;

    public boolean isMinimum(double x1, double x2) {
        // Calculate f(X1) and f(X2)
        double result1 = calculateFunctionValue(x1);
        double result2 = calculateFunctionValue(x2);

        // Is f(X1) <= f(X2)
        return result1 <= result2;
    }

    @Override
    public double calculate() {

        graphPoints.add(new Pair<>(left, right));

        double x1 = (left + right - delta) / 2;
        double x2 = (left + right + delta) / 2;
        while ((right - left) / 2 > epsilon) {
            // Calculate X1 and X2
            x1 = (left + right - delta) / 2;
            x2 = (left + right + delta) / 2;

            // Check the condition
            if (isMinimum(x1, x2)) {
                right = x2;
            } else {
                left = x1;
            }

            graphPoints.add(new Pair<>(left, right));
        }

        return (x1 + x2) / 2;
    }
}
