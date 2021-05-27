package methods;

import java.util.List;

import static methods.Vector.*;

public class QuadraticFunction implements Function {
    private Matrix a;
    private Vector b;
    private Double c;

    public QuadraticFunction(List<List<Double>> a, List<Double> b, Double c) {
        this.a = new ProfileMatrix(a);
        this.b = new Vector(b);
        this.c = c;
    }

    @Override
    public Double calculate(Vector x) {
        return dotProduct(x.product(a), x) / 2 + dotProduct(b, x) + c;
    }

    @Override
    public Vector gradient(Vector x) {
        return add(x.product(a), b);
    }

    @Override
    public Matrix getHesseMatrix() {
        return a;
    }
}
