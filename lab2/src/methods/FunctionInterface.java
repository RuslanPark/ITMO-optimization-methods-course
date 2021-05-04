package methods;

import java.util.List;

public interface FunctionInterface {
    //Returns function value in point x
    double calculateValue(List<Double> x);
    //Returns gradient norm in point x
    double calculateGradientNorm(List<Double> x);
    //Returns function gradient in point x
    List<Double> calculateGradient(List<Double> x);
    //Returns function matrix
    List<List<Double>> getMatrix();
    double getAlpha();
    List<Double> multiplyOnVector(List<Double> p);
    List<Double> getB();
}
