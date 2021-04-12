package methods;

import java.util.ArrayList;
import java.util.List;

public class GradientDescent extends AbstractMethod{
    @Override
    List<Double> calculate() {
        double fx = function.calculateValue(x);
        while (function.calculateGradientNorm(x) >= epsilon) {
            while(true) {
                List<Double> y = calculateNewPoint();
                double fy = function.calculateValue(y);
                if (fy < fx) {
                    x = y;
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
        List<Double> res = new ArrayList<>();
        for (int i = 0; i < x.size(); ++i) {
            res.add(x.get(i) - alpha * grad.get(i));
        }
        return res;
    }
}
