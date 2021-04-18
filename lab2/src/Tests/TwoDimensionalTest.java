package Tests;

import methods.*;

import java.util.*;

public class TwoDimensionalTest {
    private static List<List<Double>> constructMatrix(Double x2, Double xy, Double y2, Double x, Double y, Double c) {
        List<List<Double>> answer = new ArrayList<>();

        answer.add(new ArrayList<>());

        answer.get(0).add(c);
        answer.get(0).add(x);
        answer.get(0).add(y);

        answer.add(new ArrayList<>());

        answer.get(1).add(x);
        answer.get(1).add(x2);
        answer.get(1).add(xy);

        answer.add(new ArrayList<>());

        answer.get(2).add(y);
        answer.get(2).add(xy);
        answer.get(2).add(y2);

        return answer;
    }

    public static void main(String[] args) {
        AbstractMethod method = new SteepestDescent();

        FunctionInterface function = new Function(constructMatrix(5.0, 6.0, 5.0, 1.0, 2.0, -1.0));

        List<Double> answer = method.calculate(new ArrayList<>(Collections.nCopies(2, 1.0)), 0.0001, function);

        System.out.println("Answer is " + answer);
        System.out.println("Iterations: " + method.getPoints().size());
    }
}
