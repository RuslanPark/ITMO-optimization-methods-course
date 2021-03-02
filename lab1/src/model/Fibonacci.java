package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci extends CalculationMethod {
    private int n = 0;
    final private double epsilon;
    final private List<Long> fibNumbs = new ArrayList<>();

    public Fibonacci() {
        this.epsilon = 0.0001;
    }

    public Fibonacci(double epsilon) {
        this.epsilon = epsilon;
    }

    private void calcFibNumbs() {
        fibNumbs.add(1L);
        fibNumbs.add(1L);
        n = 2;
        double l = (right - left) / epsilon;
        while (l >= fibNumbs.get(fibNumbs.size() - 1)) {
            fibNumbs.add(fibNumbs.get(fibNumbs.size() - 1) + fibNumbs.get(fibNumbs.size() - 2));
            n++;
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
        for (int i = 1; i < n - 2; ++i) {
            if (y1 > y2) {
                left = x1;
                x1 = x2;
                x2 = left + (right - left) * ((double) fibNumbs.get(n - i - 2) / fibNumbs.get(n - i - 1));
                y1 = y2;
                y2 = calculateFunctionValue(x2);
            } else {
                right = x2;
                x2 = x1;
                x1 = left + (right - left) * ((double) fibNumbs.get(n - i - 3) / fibNumbs.get(n - i - 1));
                y2 = y1;
                y1 = calculateFunctionValue(x1);
            }
            graphPoints.add(new Pair<>(left, right));
        }
        return (left + right) / 2;
    }
}
