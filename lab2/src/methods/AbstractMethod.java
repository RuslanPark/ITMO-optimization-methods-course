package methods;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

abstract public class AbstractMethod {
    protected double epsilon = 0.0001;
    protected List<Double> x = new ArrayList<>();
    protected double alpha = 1;
    protected FunctionInterface function;
    protected List<List<Double>> points = new ArrayList<>();
    private boolean isPointsWrite = true;
    protected int iterationsCount = -1;

    //Function for launch method with the set parameters
    abstract List<Double> calculate();

    //Function for launch parameters from function arguments
    public List<Double> calculate(List<Double> x, double epsilon, FunctionInterface function) {
        this.x = x;
        this.epsilon = epsilon;
        this.function = function;
        return calculate();
    }

    //Function returns found points
    public List<List<Double>> getPoints() {
        return points;
    }

    public int getIterationsCount() {
        return iterationsCount;
    }

    public void disablePointsWriting() {
        isPointsWrite = false;
    }

    protected void writePoint(List<Double> x) {
        ++iterationsCount;
        if (isPointsWrite) {
            points.add(x);
        }
    }

    //Function returns point multiplied by constant
    protected List<Double> multiplyByConstant(List<Double> elements, Double m) {
        return elements.stream().map(x -> m * x).collect(Collectors.toList());
    }

    //Function returns sum of 2 points
    protected List<Double> add(List<Double> a, List<Double> b) {
        List<Double> answer = new ArrayList<>();
        for (int i = 0; i < a.size(); ++i) {
            answer.add(a.get(i) + b.get(i));
        }

        return answer;
    }

    //Function returns subtract of 2 points
    protected List<Double> subtract(List<Double> a, List<Double> b) {
        List<Double> answer = new ArrayList<>();
        for (int i = 0; i < a.size(); ++i) {
            answer.add(a.get(i) - b.get(i));
        }

        return answer;
    }
}
