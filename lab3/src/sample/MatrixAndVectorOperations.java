package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatrixAndVectorOperations {
    static List<Double> add(List<Double> x, List<Double> y) {
        int size = x.size();

        List<Double> result = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            result.add(x.get(i) + y.get(i));
        }

        return result;
    }

    static List<Double> sub(List<Double> x, List<Double> y) {
        int size = x.size();

        List<Double> result = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            result.add(x.get(i) - y.get(i));
        }

        return result;
    }

    static List<Double> mul_n(List<Double> x, double c) {
        return x.stream().map(xi -> xi * c).collect(Collectors.toList());
    }

    static double dotProduct(List<Double> x, List<Double> y) {
        int size = x.size();

        double result = 0;

        for (int i = 0; i < size; ++i) {
            result += x.get(i) * y.get(i);
        }

        return result;
    }

    static double norm(List<Double> x) {
        return Math.sqrt(dotProduct(x, x));
    }

    static List<Double> product(Matrix m, List<Double> v) {
        int size = v.size();

        List<Double> result = new ArrayList<>();

        for (int i = 0; i < size; ++i) {
            double value = 0;

            for (int j = 0; j < size; ++j) {
                value += m.get(i, j) * v.get(j);
            }

            result.add(value);
        }

        return result;
    }
}
