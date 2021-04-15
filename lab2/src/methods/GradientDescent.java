package methods;

import java.util.ArrayList;
import java.util.List;

public class GradientDescent extends AbstractMethod{
    @Override
    List<Double> calculate() {
        points.add(x);
        double fx = function.calculateValue(x);
        while (function.calculateGradientNorm(x) >= epsilon) {
            while(true) {
                List<Double> y = calculateNewPoint();
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

    private List<Double> calculateNewPoint() {
        List<Double> grad = function.calculateGradient(x);
        double normGrad = function.calculateGradientNorm(x);
        List<Double> res = new ArrayList<>();
        for (int i = 0; i < x.size(); ++i) {
            res.add(x.get(i) - alpha * grad.get(i) / normGrad);
        }
        return res;
    }
}
