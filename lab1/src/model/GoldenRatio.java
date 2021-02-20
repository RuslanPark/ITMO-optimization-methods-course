package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class GoldenRatio extends CalculationMethod {
    float a, b;
    float t = 0.61803f;
    float eps = 0.0000001f;

    public GoldenRatio() {
        this.a = 0;
        this.b = 1;
    }

    private double functionValue(double x) {
        return x;
    }

    private boolean isLess(double x1, double x2) {
        return functionValue(x1) < functionValue(x2);
    }

    @Override
    public List<Pair<Number, Number>> calculate() {
        List<Pair<Number, Number>> res = new ArrayList<>();
        res.add(new Pair<>(a, b));
        float x1 = a + (1 - t)*(b - a);
        float x2 = a + t * (b - a);
        while ((b - a) >= eps) {
            if (isLess(x1, x2)) {
                b = x2;
                x2 = x1;
                x1 = a + (1 - t)*(b - a);
            } else {
                a = x1;
                x1 = x2;
                x2 = a + t * (b - a);
            }
            res.add(new Pair<>(a, b));
        }
        return res;
    }
}
