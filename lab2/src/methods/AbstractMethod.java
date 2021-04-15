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

    abstract List<Double> calculate();
    public List<Double> calculate(List<Double> x, double epsilon, FunctionInterface function) {
        this.x = x;
        this.epsilon = epsilon;
        this.function = function;
        return calculate();
    }

    public List<List<Double>> getPoints() {
        return points;
    }

    protected List<Double> multiplyByConstant(List<Double> elements, Double m) {
        return elements.stream().map(x -> m * x).collect(Collectors.toList());
    }

    protected List<Double> add(List<Double> a, List<Double> b) {
        List<Double> answer = new ArrayList<>();
        for (int i = 0; i < a.size(); ++i) {
            answer.add(a.get(i) + b.get(i));
        }

        return answer;
    }

    protected List<Double> subtract(List<Double> a, List<Double> b) {
        List<Double> answer = new ArrayList<>();
        for (int i = 0; i < a.size(); ++i) {
            answer.add(a.get(i) - b.get(i));
        }

        return answer;
    }
}
