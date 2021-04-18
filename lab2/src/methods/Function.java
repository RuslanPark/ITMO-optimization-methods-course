package methods;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class Function implements FunctionInterface {

    public static List<List<Double>>  funcOneMatrix = List.of(List.of(13.0, -10.0, 30.0),
            List.of(-10.0, 64.0, 126.0),
            List.of(30.0, 126.0, 64.0)
    );

    public static double funcOneSqrt(double y, double z) {
        return 64 * z - 127 * y * y -2550 * y - 807;
    }
    public static double funcOneXFirst(double y, double z) {
        return (-sqrt(funcOneSqrt(y, z)) - 63 * y + 5) / 64;
    }
    public static double funcOneXSecond(double y, double z) {
        return (sqrt(funcOneSqrt(y, z)) - 63 * y + 5) / 64;
    }

    // Function matrix. Rows and columns format {1, x1, x2, ..., xn}.
    private List<List<Double>> matrix;
    private List<List<Double>> gradient;

    public Function(List<List<Double>> matrix) {
        this.matrix = matrix;
        findGradient();
    }

    private void findGradient() {
        gradient = new ArrayList<>();
        for (int i = 1; i < matrix.size(); ++i) {
            List<Double> row = new ArrayList<>();
            row.add(matrix.get(i).get(0));
            for (int j = 1; j < matrix.size(); ++j) {
                if (i == j) {
                    row.add(2.0 * matrix.get(i).get(j));
                } else {
                    row.add(matrix.get(i).get(j));
                }
            }
            gradient.add(row);
        }
    }

    @Override
    public double calculateValue(List<Double> x) {
        double result = 0;
        for (int i = 1; i < matrix.size(); ++i) {
            for (int j = i; j < matrix.size(); ++j) {
                result += matrix.get(i).get(j) * x.get(i - 1) * x.get(j - 1);
            }
        }

        for (int i = 1; i < matrix.size(); ++i) {
            result += matrix.get(0).get(i) * x.get(i - 1);
        }

        result += matrix.get(0).get(0);
        return result;
    }

    @Override
    public List<Double> calculateGradient(List<Double> x) {
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < gradient.size(); ++i) {
            double sum = 0;
            for (int j = 1; j <= gradient.size(); ++j) {
                sum += gradient.get(i).get(j) * x.get(j - 1);
            }
            sum += gradient.get(i).get(0);
            result.add(sum);
        }
        return result;
    }

    @Override
    public double calculateGradientNorm(List<Double> x) {
        List<Double> grad = calculateGradient(x);
        double res = 0;
        for (int i = 0; i < grad.size(); ++i) {
            res += grad.get(i) * grad.get(i);
        }
        res = Math.sqrt(res);
        return res;
    }

    @Override
    public List<List<Double>> getMatrix() {
        return matrix;
    }
}
