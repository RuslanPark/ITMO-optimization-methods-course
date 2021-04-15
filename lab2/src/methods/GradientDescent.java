package methods;

import java.util.List;

public class GradientDescent extends AbstractMethod {
    @Override
    List<Double> calculate() {
        points.add(x);
        double fx = function.calculateValue(x);
        while (function.calculateGradientNorm(x) >= epsilon) {
            while(true) {
                List<Double> y = subtract(x, multiplyByConstant(function.calculateGradient(x),
                                                            alpha / function.calculateGradientNorm(x)));
                double fy = function.calculateValue(y);
                if (fy < fx) {
                    x = y;
                    points.add(x);
                    fx = fy;
                    break;
                } else {
                    alpha /= 2;
                }
            }
        }
        return x;
    }
}
