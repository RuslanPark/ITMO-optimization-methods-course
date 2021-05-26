package methods;

import java.util.List;

public interface Function {
    double calculate(List<Double> x);
    List<Double> gradient(List<Double> x);
    List<List<Double>> getHesseMatrix();
}
