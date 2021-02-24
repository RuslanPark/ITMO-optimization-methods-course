package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class GoldenRatio extends CalculationMethod {
    double t = 0.61803f;
    double eps = 0.0000001f;

    private boolean isLess(double x1, double x2) {
        return calculateFunctionValue(x1) < calculateFunctionValue(x2);
    }

    @Override
    public double calculate() {
        graphPoints.add(new Pair<>(left, right));
        double x1 = left + (1 - t)*(right - left);
        double x2 = left + t * (right - left);
        while ((right - left) >= eps) {
            if (isLess(x1, x2)) {
                right = x2;
                x2 = x1;
                x1 = left + (1 - t)*(right - left);
            } else {
                left = x1;
                x1 = x2;
                x2 = left + t * (right - left);
            }
            graphPoints.add(new Pair<>(left, right));
        }
        return (x1 + x2) / 2;
    }
}
