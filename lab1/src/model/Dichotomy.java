package model;

import java.util.HashMap;

public class Dichotomy extends CalculationMethod {
    @Override
    public double calculate() {
        // Init the delta
        double delta = epsilon / 100;

        while ((right - left) / 2 > epsilon) {
            // Calculate X1 and X2
            double x1 = (left + right - delta) / 2;
            double x2 = (left + right + delta) / 2;

            // Add current step points for building graph
            HashMap<String, Double> hashMap = new HashMap<>();
            hashMap.put("left", left);
            hashMap.put("right", right);
            hashMap.put("x1", x1);
            hashMap.put("x2", x2);
            graphPoints.add(hashMap);

            // Check the condition and init new segment
            if (Function.calculateFunctionValue(x1) <= Function.calculateFunctionValue(x2)) {
                right = x2;
            } else {
                left = x1;
            }
        }

        return (left + right) / 2;
    }
}
