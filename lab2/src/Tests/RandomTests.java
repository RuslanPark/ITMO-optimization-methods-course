package Tests;

import methods.AbstractMethod;
import methods.DiagonalFunction;
import methods.FunctionInterface;
import methods.GradientDescent;

import java.util.*;

public class RandomTests {
    public static void main(String[] args) {
        Random random = new Random();
        for (int n = 10; n <= 10000; n *= 10) {
            for (int k = 1; k < 2000; k += 100) {
                AbstractMethod method = new GradientDescent();
                List<Double> matrix = new ArrayList<>();
                List<Double> startPoint = new ArrayList<>(Collections.nCopies(n, 1.0));
                matrix.add(1.0);
                for (int i = 1; i < n - 1; ++i) {
                    matrix.add(1.0 + (k - 1.0) * random.nextDouble());
                }
                matrix.add((double) k);
                matrix.sort(Comparator.naturalOrder());
                FunctionInterface function = new DiagonalFunction(matrix);
                method.calculate(startPoint, 0.0001, function);
                List<List<Double>> points = method.getPoints();
                System.out.println(points.size());
            }
            System.out.println("-----------------");
        }
    }
}
