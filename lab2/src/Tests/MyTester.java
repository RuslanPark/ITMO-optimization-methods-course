package Tests;

import methods.*;

import java.util.List;

public class MyTester {
    public static void main(String[] args) {
        List<List<Double>> matrix1 = List.of(
                List.of(130.0, 0.0, 0.0),
                List.of(0.0, 1.0, 0.0),
                List.of(0.0, 0.0, 1.0));
        List<List<Double>> matrix2 = List.of(
                List.of(13.0, -3.0, 9.0),
                List.of(-3.0, 1.0, 50.0),
                List.of(9.0, 50.0, 1000.0));
        List<List<Double>> matrix3 = List.of(
                List.of(-25.0, -192.0, 50.0),
                List.of(-192.0, 211.0, -420.0),
                List.of(50.0, -420.0, 211.0));
        calculateIterationsNum(matrix1);
        System.out.println("------------------------");
        calculateIterationsNum(matrix2);
        System.out.println("------------------------");
        calculateIterationsNum(matrix3);
    }

    static void calculateIterationsNum(List<List<Double>> matrix) {
        List<Double> startX = List.of(1.0, 1.0);
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
