package model;

import javafx.util.Pair;

public class Dichotomy extends CalculationMethod {
    final private double delta = 0.000001;
    final private double epsilon = 0.0001;

    @Override
    public double calculate() {

        graphPoints.add(new Pair<>(left, right));

        while ((right - left) / 2 > epsilon) {
            // Calculate X1 and X2
            double x1 = (left + right - delta) / 2;
            double x2 = (left + right + delta) / 2;

            // Check the condition
            if (isFunctionLess(x1, x2)) {
                right = x2;
            } else {
                left = x1;
            }

            graphPoints.add(new Pair<>(left, right));
        }

        return (left + right) / 2;
    }
}
