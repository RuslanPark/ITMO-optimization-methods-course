package methods;

import java.util.List;

import static methods.VectorOperations.*;

public class QuadraticFunction implements Function {
    private List<List<Double>> a;
    private List<Double> b;
    private Double c;
    private List<List<Double>> gradient;

    public QuadraticFunction(List<List<Double>> a, List<Double> b, Double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculate(List<Double> x) {
        return dotProduct(product(new ProfileMatrix(a), x), x) / 2 + dotProduct(b, x) + c;
    }

    @Override
    public List<Double> gradient(List<Double> x) {
        return add(product(new ProfileMatrix(a), x), b);
    }

    @Override
    public List<List<Double>> getHesseMatrix() {
        return a;
    }
}
