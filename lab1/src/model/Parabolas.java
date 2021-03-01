package model;

import javafx.util.Pair;
import java.util.List;

public class Parabolas extends CalculationMethod {
    private final double eps = 0.0000001;

    @Override
    public double calculate() {
        // Adding full segment
        graphPoints.add(new Pair<>(left, right));

        // Initial parameters; a < b < c
        double a = left, b = (left + right) / 2, c = right;

        // Intermediate variables
        double d, fa, fb, fc;

        while (c - a > eps) {
            // Calculating function values
            fa = calculateFunctionValue(a);
            fb = calculateFunctionValue(b);
            fc = calculateFunctionValue(c);

            // Magical formula for parabola's extremum
            d = (fa * (c * c - b * b) + fb * (a * a - c * c) + fc * (b * b - a * a)) /
                    (fa * (c - b) + fb * (a - c) + fc * (b - a)) / 2;

            // Step
            if (d < b) {
                c = b;
            } else {
                a = b;
            }

            b = d;

            // Adding segment
            graphPoints.add(new Pair<>(a, c));
        }

        return (a + c) / 2;
    }
}
