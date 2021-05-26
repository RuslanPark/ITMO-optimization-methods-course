package Tests;

import methods.*;

import java.util.List;

public class Tester {
    public static void main(String[] args) {
        List<List<Double>> matrix1 = List.of(
                List.of(130.0, 0.0, 0.0),
                List.of(0.0, 1.0, 0.0),
                List.of(0.0, 0.0, 1.0)
        );
        List<List<Double>> matrix2 = List.of(
                List.of(13.0, -10.0, 30.0),
                List.of(-10.0, 64.0, 126.0),
                List.of(30.0, 126.0, 64.0)
        );
        List<Double> startX = List.of(1.0, 1.0);
        calculateIterationsNum(matrix1, startX);
        System.out.println("------------------------");
        calculateIterationsNum(matrix2, startX);
        System.out.println("------------------------");
        startX = List.of(-4.0, 3.75);
        calculateIterationsNum(matrix2, startX);
    }

    static void calculateIterationsNum(List<List<Double>> matrix, List<Double> startX) {
        Function function = new Function(matrix);
        AbstractMethod gradientDescent = new GradientDescent();
        AbstractMethod steepestDescent = new SteepestDescent();
        AbstractMethod conjugateGradients = new ConjugateGradients();
        gradientDescent.disablePointsWriting();
        steepestDescent.disablePointsWriting();
        conjugateGradients.disablePointsWriting();
        System.out.println(gradientDescent.calculate(startX, 0.0001, function));
        System.out.println("Gradient descent: " + gradientDescent.getIterationsCount());
        System.out.println(steepestDescent.calculate(startX, 0.0001, function));
        System.out.println("Steepest descent: " + steepestDescent.getIterationsCount());
        System.out.println(conjugateGradients.calculate(startX, 0.0001, function));
        System.out.println("Conjugate gradients: " + conjugateGradients.getIterationsCount());
    }
}
