package model;

import javafx.util.Pair;

import java.util.HashMap;

public class GoldenRatio extends CalculationMethod {
    //
    final private double phi = 0.61803;

    @Override
    public double calculate() {
        // Init x1 and x2
        double x1 = right - phi*(right - left);
        double x2 = left + phi * (right - left);
        double y1 = calculateFunctionValue(x1);
        double y2 = calculateFunctionValue(x2);
        // Calculate until get the required accuracy
        while ((right - left) / 2 > epsilon) {

            HashMap<String, Double> hashMap = new HashMap<>();
            hashMap.put("left", left);
            hashMap.put("right", right);
            hashMap.put("x1", x1);
            hashMap.put("x2", x2);
            graphPoints.add(hashMap);
            // Checking at which point the function is smaller
            if (y1 <= y2) {
                right = x2;
                x2 = x1;
                x1 = right - phi*(right - left);
                y2 = y1;
                y1 = calculateFunctionValue(x1);
            } else {
                left = x1;
                x1 = x2;
                x2 = left + phi * (right - left);
                y1 = y2;
                y2 = calculateFunctionValue(x2);
            }
        }
        // Return min function
        return (left + right) / 2;
    }
}
