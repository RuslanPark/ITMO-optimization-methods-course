package model;

import javafx.util.Pair;

public class GoldenRatio extends CalculationMethod {
    //
    final private double t = 0.61803f;
    //
    final private double eps = 0.0000001f;

    @Override
    public double calculate() {
        // Add first full segment under study
        graphPoints.add(new Pair<>(left, right));
        // Init x1 and x2
        double x1 = left + (1 - t)*(right - left);
        double x2 = left + t * (right - left);
        // Calculate until get the required accuracy
        while ((right - left) >= eps) {
            // Checking at which point the function is smaller
            if (isFunctionLess(x1, x2)) {
                right = x2;
                x2 = x1;
                x1 = left + (1 - t)*(right - left);
            } else {
                left = x1;
                x1 = x2;
                x2 = left + t * (right - left);
            }
            // Add segment
            graphPoints.add(new Pair<>(left, right));
        }
        // Return min function
        return (x1 + x2) / 2;
    }
}
