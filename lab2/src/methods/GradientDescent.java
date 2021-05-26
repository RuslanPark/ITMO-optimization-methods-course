package methods;

import java.util.List;

public class GradientDescent extends AbstractMethod {
    @Override
    List<Double> calculate() {
        //Add x_0 in list
        writePoint(x);
        //Calculate function value in point x_0
        double fx = function.calculateValue(x);

        List<Double> gradient = function.calculateGradient(x);
        double functionGradientNorm = function.calculateGradientNorm(x);
        while (functionGradientNorm >= epsilon) {
            List<Double> y = subtract(x, multiplyByConstant(gradient, alpha / functionGradientNorm));
            double fy = function.calculateValue(y);

            if (fy < fx) {
                x = y;
                writePoint(x);
                fx = fy;
                functionGradientNorm = function.calculateGradientNorm(x);
                gradient = function.calculateGradient(x);
            } else {
                alpha /= 2;
            }
        }
        return x;
    }
}
