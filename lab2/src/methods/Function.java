package methods;

import java.util.ArrayList;
import java.util.List;

public class Function implements FunctionInterface{
    private List<List<Double>> squareMatrix;
    private List<Double> firstDegreesMatrix;
    private double c;
    private List<List<Double>> gradient;
    private List<Double> gc = new ArrayList<>();

    public Function(List<List<Double>> squareMatrix, List<Double> firstDegreesMatrix, double c) {
        this.squareMatrix = squareMatrix;
        this.firstDegreesMatrix = firstDegreesMatrix;
        this.c = c;
        findGradient();
    }

    private void findGradient() {
        gradient = new ArrayList<>();
        for (int i = 0; i < squareMatrix.size(); ++i) {
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < squareMatrix.size(); ++j) {
                if (i == j) {
                    row.add(2.0 * squareMatrix.get(i).get(j));
                } else {
                    row.add(squareMatrix.get(i).get(j));
                }
            }
            gradient.add(row);
        }

        for (int i = 0; i < firstDegreesMatrix.size(); ++i) {
            gc.add(firstDegreesMatrix.get(i));
        }
    }

    @Override
    public double calculateValue(List<Double> x) {
        double result = 0;
        for (int i = 0; i < squareMatrix.size(); ++i) {
            for (int j = i; j < squareMatrix.size(); ++j) {
                result += squareMatrix.get(i).get(j) * x.get(i) * x.get(j);
            }
        }

        for (int i = 0; i < firstDegreesMatrix.size(); ++i) {
            result += firstDegreesMatrix.get(i) * x.get(i);
        }

        result += c;
        return result;
    }

    @Override
    public List<Double> calculateGradient(List<Double> x) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < gradient.size(); ++i) {
            double sum = 0;
            for (int j = 0; j < gradient.size(); ++j) {
                sum += gradient.get(i).get(j) * x.get(j);
            }
            sum += gc.get(i);
            result.add(sum);
        }
        return result;
    }

    @Override
    public double calculateGradientNorm(List<Double> x) {
        double res = 0;
        for (int i = 0; i < gradient.size(); ++i) {
            double row = 0;
            for (int j = 0; j < gradient.size(); ++j) {
                row += gradient.get(i).get(j) * x.get(j);
            }
            row += gc.get(i);
            res += row * row;
        }
        res = Math.sqrt(res);
        return res;
    }
}
