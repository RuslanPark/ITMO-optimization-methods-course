package model;

import javafx.util.Pair;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;

public class Dichotomy extends CalculationMethod {

    float left, right;
    float delta = (float) 0.01;
    float epsilon = (float) 0.0001;
    String expression;

    public Dichotomy(float left, float right, String expression) {
        this.left = left;
        this.right = right;
        this.expression = expression;
    }

    public boolean isMinimum(float x1, float x2) {
        Expression e = new ExpressionBuilder(expression)
                .variable("x")
                .build()
                .setVariable("x", x1);
        double result1 = e.evaluate();
        e.setVariable("x", x2);
        double result2 = e.evaluate();

        return result1 <= result2;
    }

    @Override
    public List< Pair<Number, Number> > calculate() {

        List< Pair<Number, Number> > list = new ArrayList<>();

        list.add(new Pair<>(left, right));
        while ((right - left / 2) > epsilon) {
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
