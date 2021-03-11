package model;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;

public class Parabolas extends CalculationMethod {
    private final double eps = 0.0000001;

    @Override
    public double calculate() {
        // Adding full segment

        // Initial parameters; a < b < c
        double x1 = left, x2 = 0.3, x3 = right;

        // Intermediate variables
        double f1 = calculateFunctionValue(x1);
        double f2 = calculateFunctionValue(x2);
        double f3 = calculateFunctionValue(x3);

        double xPrev = 0;
        double x;
        boolean firstIteration = true;
        while (true) {
            double a0 = f1;
            double a1 = (f2 - f1) / (x2 - x1);
            double a2 = (((f3 - f1) / (x3 - x1)) - ((f2 - f1) / (x2 - x1))) / (x3 - x2);
            x = (x1 + x2 - a1 / a2) / 2;

            System.out.println(a2 + " " + a1 + " " + a0);

            HashMap<String, Double> hashMap = new HashMap<>();
            hashMap.put("left", x1);
            hashMap.put("right", x3);
            hashMap.put("a0", a0);
            hashMap.put("a1", a1);
            hashMap.put("a2", a2);
            hashMap.put("x1", x1);
            hashMap.put("x2", x2);
            graphPoints.add(hashMap);
            if (firstIteration) {
                firstIteration = false;
            } else if (Math.abs(x - xPrev) < epsilon) {
                return x;
            }
            xPrev = x;

            double fx = calculateFunctionValue(x);

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
