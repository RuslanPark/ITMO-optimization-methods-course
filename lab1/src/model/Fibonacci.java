package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fibonacci extends CalculationMethod {
    private int n = 0;
    final private List<Long> fibNumbs = new ArrayList<>();

    // Function for finding the desired number of fibonacci numbers
    private void calcFibNumbs() {
        // Add first and second fibonacci numbers
        fibNumbs.add(1L);
        fibNumbs.add(1L);
        n = 2;

        // Init limit for fibonacci numbers
        double l = (right - left) / epsilon;

        // Calculate fibonacci numbers
        while (l >= fibNumbs.get(fibNumbs.size() - 1)) {
            fibNumbs.add(fibNumbs.get(fibNumbs.size() - 1) + fibNumbs.get(fibNumbs.size() - 2));
            n++;
        }
    }

    @Override
    public double calculate() {
        // Calculate fibonacci numbers
        calcFibNumbs();

        // Init x1, x2 and values in these points
        double x1 = left + (right - left) * ((double) fibNumbs.get(n - 3) / fibNumbs.get(n - 1));
        double x2 = left + (right - left) * ((double) fibNumbs.get(n - 2) / fibNumbs.get(n - 1));
        double y1 = Function.calculateFunctionValue(x1);
        double y2 = Function.calculateFunctionValue(x2);

        for (int i = 1; i < n - 2; ++i) {
            // Add current step points for building graph
            HashMap<String, Double> hashMap = new HashMap<>();
            hashMap.put("left", left);
            hashMap.put("right", right);
            hashMap.put("x1", x1);
            hashMap.put("x2", x2);
            graphPoints.add(hashMap);

            // Checking at which point the function is smaller and init new segment
            if (y1 > y2) {
                left = x1;
                x1 = x2;
                x2 = left + (right - left) * ((double) fibNumbs.get(n - i - 2) / fibNumbs.get(n - i - 1));
                y1 = y2;
                y2 = Function.calculateFunctionValue(x2);
            } else {
                right = x2;
                x2 = x1;
                x1 = left + (right - left) * ((double) fibNumbs.get(n - i - 3) / fibNumbs.get(n - i - 1));
                y2 = y1;
                y1 = Function.calculateFunctionValue(x1);
            }
        }

        return (left + right) / 2;
    }
}
