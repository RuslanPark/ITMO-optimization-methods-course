package methods;

import java.util.ArrayList;
import java.util.List;

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
}
