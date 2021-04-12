package methods;

import java.util.ArrayList;
import java.util.List;

public class DiagonalFunction implements FunctionInterface {
    private List<Double> squareMatrix;
    private double c;
    private List<Double> gradient;
    private List<Double> gc = new ArrayList<>();

    public DiagonalFunction(List<Double> squareMatrix, double c) {
        this.squareMatrix = squareMatrix;
        this.c = c;
        findGradient();
    }

    private void findGradient() {
        gradient = new ArrayList<>();
        for (int i = 0; i < squareMatrix.size(); ++i) {
            gradient.add(2.0 * squareMatrix.get(i));
        }
    }

    @Override
    public double calculateValue(List<Double> x) {
        double result = 0;
        for (int i = 0; i < squareMatrix.size(); ++i) {
            result += squareMatrix.get(i) * x.get(i) * x.get(i);
        }
        return result;
    }

    @Override
    public List<Double> calculateGradient(List<Double> x) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < gradient.size(); ++i) {
            double sum = 0;
            result.add(gradient.get(i) * x.get(i));
        }
        return result;
    }

    @Override
    public double calculateGradientNorm(List<Double> x) {
        double res = 0;
        for (int i = 0; i < gradient.size(); ++i) {
            double row = 0;
                row = gradient.get(i) * x.get(i);
            res += row * row;
        }
        res = Math.sqrt(res);
        return res;
    }
}
