package methods;

import java.util.ArrayList;
import java.util.List;

public class DiagonalFunction implements FunctionInterface {
    private List<Double> matrix;
    private List<Double> gradient;

    public DiagonalFunction(List<Double> matrix) {
        this.matrix = matrix;
        findGradient();
    }

    private void findGradient() {
        gradient = new ArrayList<>();
        for (int i = 0; i < matrix.size(); ++i) {
            gradient.add(2.0 * matrix.get(i));
        }
    }

    @Override
    public double calculateValue(List<Double> x) {
        double result = 0;
        for (int i = 0; i < matrix.size(); ++i) {
            result += matrix.get(i) * x.get(i) * x.get(i);
        }
        return result;
    }

    @Override
    public List<Double> calculateGradient(List<Double> x) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < gradient.size(); ++i) {
            result.add(gradient.get(i) * x.get(i));
        }
        return result;
    }

    @Override
    public double calculateGradientNorm(List<Double> x) {
        double res = 0;
        List<Double> grad = calculateGradient(x);
        for (int i = 0; i < grad.size(); ++i) {
            res += grad.get(i) * grad.get(i);
        }
        res = Math.sqrt(res);
        return res;
    }
}
