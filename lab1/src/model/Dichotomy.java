package model;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class Dichotomy extends CalculationMethod {
    private double delta;

    @Override
    public double calculate() {
        delta = epsilon / 10;

        while ((right - left) / 2 > epsilon) {
            // Calculate X1 and X2
            double x1 = (left + right - delta) / 2;
            double x2 = (left + right + delta) / 2;

            HashMap<String, Double> hashMap = new HashMap<>();
            hashMap.put("left", left);
            hashMap.put("right", right);
            hashMap.put("x1", x1);
            hashMap.put("x2", x2);
            graphPoints.add(hashMap);
            // Check the condition
            if (isFunctionLess(x1, x2)) {
                right = x2;
            } else {
                left = x1;
            }
        }

        return (left + right) / 2;
    }
}
