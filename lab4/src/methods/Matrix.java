package methods;

public interface Matrix {
    Double get(Integer i, Integer j);
    void set(Integer i, Integer j, Double value);
    Integer size();
    Matrix add(Matrix m1);
    Matrix sub(Matrix m1);
    Matrix mul_n(Double c);
    Matrix divide_n(Double c);
}
