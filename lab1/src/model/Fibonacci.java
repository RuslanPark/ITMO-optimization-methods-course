package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci extends CalculationMethod {
    int n = 100;
    List<Integer> fibNumbs = new ArrayList<>();

    private void calcFibNumbs() {
        fibNumbs.add(1);
        fibNumbs.add(1);
        for (int i = 2; i < n; ++i) {
            fibNumbs.add(fibNumbs.get(i - 1) + fibNumbs.get(i - 2));
        }
    }

    @Override
    public double calculate() {
        calcFibNumbs();
        graphPoints.add(new Pair<>(left, right));
        double x1 = left + (right - left) * ((double) fibNumbs.get(n - 3) / fibNumbs.get(n - 1));
        double x2 = left + (right - left) * ((double) fibNumbs.get(n - 2) / fibNumbs.get(n - 1));
        double y1 = calculateFunctionValue(x1);
        double y2 = calculateFunctionValue(x2);
        for (int i = n - 1; i > 1; --i) {
            if (y1 > y2) {
                left = x1;
                x1 = x2;
                x2 = right - (x1 - left);
                y1 = y2;
                y2 = calculateFunctionValue(x2);
            } else {
                right = x2;
                x2 = x1;
                x1 = left + (right - x2);
                y2 = y1;
                y1 = calculateFunctionValue(x1);
            }
            graphPoints.add(new Pair<>(left, right));
        }
        return (x1 + x2) / 2;
    }
}
