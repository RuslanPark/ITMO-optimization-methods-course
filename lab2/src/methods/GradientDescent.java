package methods;

import java.util.List;

public class GradientDescent extends AbstractMethod {
    @Override
    List<Double> calculate() {
        //Add x_0 in list
        writePoint(x);
        //Calculate function value in point x_0
        double fx = function.calculateValue(x);

        alpha = function.getAlpha();
        double functionGradientNorm = function.calculateGradientNorm(x);
        while (functionGradientNorm >= epsilon) {
            List<Double> y = subtract(x, multiplyByConstant(function.calculateGradient(x), alpha / functionGradientNorm));
            double fy = function.calculateValue(y);

            if (fy < fx) {
                x = y;
                writePoint(x);
                fx = fy;
                functionGradientNorm = function.calculateGradientNorm(x);
            } else {
                alpha /= 2;
            }
        }
        return x;
    }
}
