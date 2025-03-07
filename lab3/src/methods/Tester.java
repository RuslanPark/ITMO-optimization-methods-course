package methods;

import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
        /*task2();
        task3();
        task4();*/
        //task5Random();
        task6Gilbert();
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
            for (int k = 1; k <= 5; k++) {
                generator.task2Regenerate(k, "task2n" + n + "k" + k);
                task2check("task2n" + n + "k" + k, x, n, k);
            }
        }
    }

    private static void task2check(String directory, List<Double> x, int n, int k) {
        LUDecomposition.calculate(directory);
        double norm = calcNorm(directory, x);
        double normX = calcXNorm(x);

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
        double norm = calcNorm(directory, x);
        double normX = calcXNorm(x);

        System.out.println(k + " " + norm + " " + norm / normX);
    }

    private static void task4() {
        MatrixGenerator generator = new MatrixGenerator();
        for (int n = 1; n <= 1001; n += 100) {
            List<Double> x = new ArrayList<>();
            for (int i = 1; i <= n; ++i) {
                x.add((double) i);
            }
            generator.task4Generate("task4n" + n, n);
            task4check("task4n" + n, x, n);
        }
    }

    private static void task4check(String directory, List<Double> x, int n) {
        LUDecomposition.calculate(directory);
        double normLU = calcNorm(directory, x);
        GaussMethod.calculate(directory);
        double normGauss= calcNorm(directory, x);

        System.out.println(n + " " + normLU + " " + normGauss);
    }

    private static void task5Random() {
        MatrixGenerator generator = new MatrixGenerator();
        for (int n = 1; n <= 100; n += 10) {
            List<Double> x = new ArrayList<>();
            List<Double> xx = new ArrayList<>();
            for (int i = 1; i <= n; ++i) {
                x.add((double) i);
                xx.add(0.2);
            }

            List<Double> res = ConjugateGradientsMethod.solve(generator.task5Generate("task4n" + n, n), generator.task5F, xx, 0.0000001);
            for (Double i : res) {
                System.out.print(i + " ");
            }
            System.out.println("\n---------\n");
        }
    }

    private static void task6Gilbert() {
        MatrixGenerator generator = new MatrixGenerator();
        for (int n = 10; n <= 1000; n += 100) {
            List<Double> x = new ArrayList<>();
            List<Double> xx = new ArrayList<>();
            for (int i = 1; i <= n; ++i) {
                x.add((double) i);
                xx.add(0.0002);
            }
            List<Double> res = ConjugateGradientsMethod.solve(generator.task6Generate("task4n" + n, n), generator.task6F, xx, 0.0000001);
            for (Double i : res) {
                System.out.print(i + " ");
            }
            System.out.println("\n---------\n");
        }
    }

    private static double calcNorm(String directory, List<Double> x) {
        List<Double> res = Util.readFile(directory, "res.txt");
        double norm = 0;
        for (int i = 0; i < res.size(); ++i) {
            norm += Math.pow(x.get(i) - res.get(i), 2);
        }
        return Math.sqrt(norm);
    }

    private static double calcXNorm(List<Double> x) {
        double normX = 0;
        for (Double aDouble : x) {
            normX += aDouble * aDouble;
        }
        return Math.sqrt(normX);
    }
}
