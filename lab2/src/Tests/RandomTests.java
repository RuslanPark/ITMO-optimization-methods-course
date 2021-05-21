package Tests;

import methods.*;

import java.util.*;

public class RandomTests {
    public static void main(String[] args) {
        Random random = new Random();
        for (int n = 10; n <= 10000; n *= 10) {
            for (int k = 1; k <= 1001; k += 100) {
                int sum = 0;
                for (int j = 0; j < 10; ++j) {
                    AbstractMethod method = new ConjugateGradients();
                    method.disablePointsWriting();
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
                    sum += method.getIterationsCount();
                }
                System.out.println(sum / 10);
            }
            System.out.println("-----------------");
        }
    }
}
