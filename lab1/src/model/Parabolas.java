package model;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class Parabolas extends CalculationMethod {
    @Override
    public double calculate() {
        // Initial parameters; x1 < x2 < x3
        double x1 = left, x2 = 0.5, x3 = right;

        // Calculate function value in x1, x2, x3
        double f1 = Function.calculateFunctionValue(x1);
        double f2 = Function.calculateFunctionValue(x2);
        double f3 = Function.calculateFunctionValue(x3);

        // Init x_i-1, x_i and check for firstIteration
        double xPrev = 0;
        double x = 0.0f;
        boolean firstIteration = true;

        while (true) {
            // Calculate parabola coefficients
            double a0 = f1;
            double a1 = (f2 - f1) / (x2 - x1);
            double a2 = (((f3 - f1) / (x3 - x1)) - ((f2 - f1) / (x2 - x1))) / (x3 - x2);

            x = (x1 + x2 - a1 / a2) / 2;

            // Add current step points for building graph
            HashMap<String, Double> hashMap = new HashMap<>();
            hashMap.put("left", x1);
            hashMap.put("right", x3);
            hashMap.put("a0", a0);
            hashMap.put("a1", a1);
            hashMap.put("a2", a2);
            hashMap.put("x2", x2);
            graphPoints.add(hashMap);

            double fx = Function.calculateFunctionValue(x);

            // Checking distance between points on all iterations except the first one
            if (firstIteration) {
                firstIteration = false;
            } else if (Math.abs(x - xPrev) < epsilon) {
                return x;
            }
            xPrev = x;

            // Init new points
            if (x1 < x && x < x2 && x2 < x3) {
                if (fx >= f2) {
                    x1 = x;
                    f1 = fx;
                } else {
                    x3 = x2;
                    f3 = f2;
                    x2 = x;
                    f2 = fx;
                }
            } else if (x1 < x2 && x2 < x && x < x3) {
                if (f2 >= fx) {
                    x1 = x2;
                    f1 = f2;
                    x2 = x;
                    f2 = fx;
                } else {
                    x3 = x;
                    f3 = fx;
                }
            }
        }
    }
}
