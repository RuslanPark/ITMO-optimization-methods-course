package methods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class DiagonalFunction implements FunctionInterface {
    private final List<Double> matrix;
    private final List<Double> gradient;

    public DiagonalFunction(List<Double> matrix) {
        this.matrix = matrix;

        gradient = new ArrayList<>();
        for (Double coefficient : matrix) {
            gradient.add(2.0 * coefficient);
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
        for (int i = 0; i < gradient.size(); ++i) {
            res += Math.pow(gradient.get(i) * x.get(i), 2);
        }
        res = Math.sqrt(res);
        return res;
    }

    @Override
    public List<Double> multiplyOnVector(List<Double> p) {
        List<Double> answer = new ArrayList<>();
        for (int i = 0; i < matrix.size(); ++i) {
            answer.add(2 * matrix.get(i) * p.get(i));
        }

        return answer;
    }
}
