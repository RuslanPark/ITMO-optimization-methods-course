package methods;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;

public class Function implements FunctionInterface {

    public static List<List<Double>> funcMatrix(String chose) {
        switch (chose) {
            case ("first") -> {
                return List.of(
                        List.of(130.0, 0.0, 0.0),
                        List.of(0.0, 1.0, 0.0),
                        List.of(0.0, 0.0, 1.0)
                );
            }
            case ("second") -> {
                return List.of(
                        List.of(13.0, -10.0, 30.0),
                        List.of(-10.0, 64.0, 126.0),
                        List.of(30.0, 126.0, 64.0)
                );
            }
            default -> {
                return List.of(
                        List.of(97.0, 0.0, 0.0),
                        List.of(0.0, 1.0, -1.999),
                        List.of(0.0, -1.999, 1.0)
                );
            }
        }
    }

    public static double funcSqrt(String chose, double y, double z) {
        switch (chose) {
            case ("first") -> {
                return z - y * y - 130;
            }
            case ("second") -> {
                return 64 * z - 127 * y * y -2550 * y - 807;
            }
            default -> {
                return 4_000_000 * z - 3999 * y * y - 388_000_000;
            }
        }
    }
    public static double funcXFirst(String chose, double y, double z) {

        switch (chose) {
            case ("first") -> {
                return -sqrt(funcSqrt(chose, y, z));
            }
            case ("second") -> {
                return (-sqrt(funcSqrt(chose, y, z)) - 63 * y + 5) / 64;
            }
            default -> {
                return (-sqrt(funcSqrt(chose, y, z)) + 1999 * y) / 2000;
            }
        }
    }
    public static double funcXSecond(String chose, double y, double z) {
        switch (chose) {
            case ("first") -> {
                return sqrt(funcSqrt(chose, y, z));
            }
            case ("second") -> {
                return (sqrt(funcSqrt(chose, y, z)) - 63 * y + 5) / 64;
            }
            default -> {
                return (sqrt(funcSqrt(chose, y, z)) + 1999 * y) / 2000;
            }
        }
    }

    // Function matrix. Rows and columns format {1, x1, x2, ..., xn}.
    private final List<List<Double>> matrix;
    private final List<List<Double>> gradient;

    public Function(List<List<Double>> matrix) {
        this.matrix = matrix;

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
        for (Double coefficient : grad) {
            res += coefficient * coefficient;
        }
        res = Math.sqrt(res);
        return res;
    }

    @Override
    public List<Double> multiplyOnVector(List<Double> p) {
        List<Double> answer = new ArrayList<>();
        for (int i = 1; i < matrix.size(); ++i) {
            double sum = 0;
            for (int j = 1; j < matrix.size(); ++j) {
                sum += matrix.get(i).get(j) * p.get(j - 1);
                if (i == j) sum += matrix.get(i).get(j) * p.get(j - 1);
            }
            answer.add(sum);
        }
        return answer;
    }
}
