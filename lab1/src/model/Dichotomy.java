package model;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class Dichotomy extends CalculationMethod {

    float left, right;
    float delta = (float) 0.000001;
    float epsilon = (float) 0.0001;

    public Dichotomy() {
        this.left = -2;
        this.right = 3;
    }

    public boolean isMinimum(float x1, float x2) {
        float result1 = (float) (x1 * x1 + Math.exp(-0.35 * x1));
        float result2 = (float) (x2 * x2 + Math.exp(-0.35 * x2));
        return result1 <= result2;
    }

    @Override
    public List< Pair<Number, Number> > calculate() {

        List< Pair<Number, Number> > list = new ArrayList<>();

        list.add(new Pair<>(left, right));
        while ((right - left) / 2 > epsilon) {
            float x1 = (left + right - delta) / 2;
            float x2 = (left + right + delta) / 2;
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
