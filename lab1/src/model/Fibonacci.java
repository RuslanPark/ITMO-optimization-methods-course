package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci extends CalculationMethod {
    private double a, b;
    int n = 100;
    List<Integer> fibNumbs = new ArrayList<>();

    public Fibonacci() {
        this.a = 0.1;
        this.b = 2.5;
    }

    private double functionValue(double x) {
        return 10 * x * Math.log(x) - x * x / 2;
    }

    private void calcFibNumbs() {
        fibNumbs.add(1);
        fibNumbs.add(1);
        for (int i = 2; i < n; ++i) {
            fibNumbs.add(fibNumbs.get(i - 1) + fibNumbs.get(i - 2));
        }
    }

    @Override
    public List<Pair<Number, Number>> calculate() {
        calcFibNumbs();
        List<Pair<Number, Number>> res = new ArrayList<>();
        res.add(new Pair<>(a, b));
        double x1 = a + (b - a) * ((double) fibNumbs.get(n - 3) / fibNumbs.get(n - 1));
        double x2 = a + (b - a) * ((double) fibNumbs.get(n - 2) / fibNumbs.get(n - 1));
        double y1 = functionValue(x1);
        double y2 = functionValue(x2);
        for (int i = n - 1; i > 1; --i) {
            if (y1 > y2) {
                a = x1;
                x1 = x2;
                x2 = b - (x1 - a);
                y1 = y2;
                y2 = functionValue(x2);
            } else {
                b = x2;
                x2 = x1;
                x1 = a + (b - x2);
                y2 = y1;
                y1 = functionValue(x1);
            }
            res.add(new Pair<>(a, b));
        }
        return res;
    }
}
