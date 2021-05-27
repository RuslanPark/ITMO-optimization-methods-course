package methods;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Vector {
    private List<Double> v;

    public Vector() {
        this.v = new ArrayList<>();
    }

    public Vector(Integer capacity) {
        this.v = new ArrayList<>(capacity);
    }

    public Vector(List<Double> v) {
        this.v = v;
    }

    Integer size() {
        return v.size();
    }

    Double get(Integer i) {
        return v.get(i);
    }

    void set(Integer i, Double value) {
        v.set(i, value);
    }

    void add(Double value) {
        v.add(value);
    }

    Double norm() {
        return Math.sqrt(dotProduct(this, this));
    }

    Vector product(Matrix m) {
        int size = m.size();

        Vector result = new Vector(size);

        for (int i = 0; i < size; ++i) {
            double value = 0;

            for (int j = 0; j < size; ++j) {
                value += m.get(i, j) * v.get(j);
            }

            result.add(value);
        }

        return result;
    }

    @Override
    public String toString() {
        return v.toString();
    }

    static double dotProduct(Vector x, Vector y) {
        double result = 0;

        for (int i = 0; i < x.size(); ++i) {
            result += x.get(i) * y.get(i);
        }

        return result;
    }

    static Vector add(Vector x, Vector y) {
        int size = x.size();

        Vector result = new Vector(size);

        for (int i = 0; i < size; ++i) {
            result.add(x.get(i) + y.get(i));
        }

        return result;
    }

    static Vector sub(Vector x, Vector y) {
        int size = x.size();

        Vector result = new Vector(size);

        for (int i = 0; i < size; ++i) {
            result.add(x.get(i) - y.get(i));
        }

        return result;
    }

    static Vector mul_n(Vector x, Double c) {
        return new Vector(x.v.stream().map(xi -> xi * c).collect(Collectors.toList()));
    }
}
