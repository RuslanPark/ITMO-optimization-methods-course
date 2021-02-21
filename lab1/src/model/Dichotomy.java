package model;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class Dichotomy extends CalculationMethod {

    double left, right;
    double delta = 0.0001;
    double epsilon = 0.0001;

    public Dichotomy() {
        this.left = 0.1;
        this.right = 2.5;
    }

    public boolean isMinimum(double x1, double x2) {
        // Calculate f(X1) and f(X2)
        double result1 = 10 * x1 * Math.log(x1) + x1 * x1 / 2;
        double result2 = 10 * x2 * Math.log(x2) + x2 * x2 / 2;

        // Is f(X1) <= f(X2)
        return result1 <= result2;
    }

    @Override
    public List< Pair<Number, Number> > calculate() {

        List< Pair<Number, Number> > list = new ArrayList<>();
        list.add(new Pair<>(left, right));

        while ((right - left) / 2 > epsilon) {
            // Calculate X1 and X2
            double x1 = (left + right - delta) / 2;
            double x2 = (left + right + delta) / 2;

            // Check the condition
            if (isMinimum(x1, x2)) {
                right = x2;
            } else {
                left = x1;
            }

            list.add(new Pair<>(left, right));
        }

        return list;
    }
}
