package methods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

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
        for (int i = 0; i < gradient.size(); ++i) {
            res += Math.pow(gradient.get(i) * x.get(i), 2);
        }
        res = Math.sqrt(res);
        return res;
    }

    @Override
    public List<List<Double>> getMatrix() {
        List<List<Double>> answer = new ArrayList<>();

        answer.add(new ArrayList<>());

        for (int i = 0; i <= matrix.size(); i++) {
            answer.get(0).add(0.0);
        }

        for (int i = 1; i <= matrix.size(); i++)
        {
            answer.add(new ArrayList<>());
            answer.get(i).add(0.0);
            for (int j = 1; j <= matrix.size(); j++)
            {
                if (i == j) {
                    answer.get(i).add(matrix.get(i - 1));
                } else {
                    answer.get(i).add(0.0);
                }
            }
        }

        return answer;
    }

    @Override
    public double getAlpha() {
        double min = IntStream.range(0, matrix.size())
                .mapToDouble(index -> matrix.get(index))
                .min()
                .orElseThrow();
        double max = IntStream.range(0, matrix.size())
                .mapToDouble(index -> matrix.get(index))
                .max()
                .orElseThrow();
        return 2 / (min + max);
    }

    @Override
    public List<Double> multiplyOnVector(List<Double> p) {
        List<Double> answer = new ArrayList<>();
        for (int i = 0; i < matrix.size(); ++i) {
            answer.add(2 * matrix.get(i) * p.get(i));
        }

        return answer;
    }

    @Override
    public List<Double> getB() {
        return new ArrayList<Double>(Collections.nCopies(matrix.size(), 0.0));
    }
}
