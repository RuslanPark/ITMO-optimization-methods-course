package sample;

import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
        //task2();
        task3();
    }

    private static void task2() {
        MatrixGenerator generator = new MatrixGenerator();
        for (int n = 10; n <= 1000; n *= 10) {
            List<Double> x = new ArrayList<>();
            for (int i = 1; i <= n; ++i) {
                x.add((double) i);
            }
            generator.task2Generate("task2n" + n + "k" + 0, n, 0);
            task2check("task2n" + n + "k" + 0, x, n, 0);
            for (int k = 1; k <= 0; k++) {
                generator.task2Regenerate(k, "task2n" + n + "k" + k);
                task2check("task2n" + n + "k" + k, x, n, k);
            }
        }
    }

    private static void task2check(String directory, List<Double> x, int n, int k) {
        LUDecomposition.calculate(directory);
        List<Double> res = Util.readFile(directory, "res.txt");
        double norm = 0;
        for (int i = 0; i < res.size(); ++i) {
            norm += Math.pow(x.get(i) - res.get(i), 2);
        }
        norm = Math.sqrt(norm);

        double normX = 0;
        for (int i = 0; i < x.size(); ++i) {
            normX += x.get(i) * x.get(i);
        }
        normX = Math.sqrt(normX);

        System.out.println(n + " " + k + " " + norm + " " + norm / normX);
    }

    private static void task3() {
        MatrixGenerator generator = new MatrixGenerator();
        for (int k = 1; k <= 200; k += 10) {
            List<Double> x = new ArrayList<>();
            for (int i = 1; i <= k; ++i) {
                x.add((double) i);
            }
            generator.task3Generate("task3k" + k, k);
            task3check("task3k" + k, x, k);
        }
    }

    private static void task3check(String directory, List<Double> x, int k) {
        LUDecomposition.calculate(directory);
        List<Double> res = Util.readFile(directory, "res.txt");
        System.out.println(res);
        double norm = 0;
        for (int i = 0; i < res.size(); ++i) {
            norm += Math.pow(x.get(i) - res.get(i), 2);
        }
        norm = Math.sqrt(norm);

        double normX = 0;
        for (int i = 0; i < x.size(); ++i) {
            normX += x.get(i) * x.get(i);
        }
        normX = Math.sqrt(normX);

        //System.out.println(k + " " + norm + " " + norm / normX);
    }
}
