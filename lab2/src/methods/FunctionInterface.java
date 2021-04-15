package methods;

import java.util.List;

public interface FunctionInterface {
    double calculateValue(List<Double> x);
    double calculateGradientNorm(List<Double> x);
    List<Double> calculateGradient(List<Double> x);
    List<List<Double>> getMatrix();
}
