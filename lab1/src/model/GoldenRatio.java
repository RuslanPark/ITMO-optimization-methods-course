package model;

import javafx.util.Pair;

import java.util.HashMap;

public class GoldenRatio extends CalculationMethod {

    @Override
    public double calculate() {
        // Init constant for golden ratio
        double phi = 0.61803;

        // Init x1, x2 and function values in these points
        double x1 = right - phi*(right - left);
        double x2 = left + phi * (right - left);
        double y1 = Function.calculateFunctionValue(x1);
        double y2 = Function.calculateFunctionValue(x2);

        // Calculate until get the required accuracy
        while ((right - left) / 2 > epsilon) {
            // Add current step points for building graph
            HashMap<String, Double> hashMap = new HashMap<>();
            hashMap.put("left", left);
            hashMap.put("right", right);
            hashMap.put("x1", x1);
            hashMap.put("x2", x2);
            graphPoints.add(hashMap);

            // Checking at which point the function is smaller and init new segment
            if (y1 <= y2) {
                right = x2;
                x2 = x1;
                x1 = right - phi*(right - left);
                y2 = y1;
                y1 = Function.calculateFunctionValue(x1);
            } else {
                left = x1;
                x1 = x2;
                x2 = left + phi * (right - left);
                y1 = y2;
                y2 = Function.calculateFunctionValue(x2);
            }
        }

        return (left + right) / 2;
    }
}
