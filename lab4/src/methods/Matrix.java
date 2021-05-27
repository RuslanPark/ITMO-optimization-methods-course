package methods;

public interface Matrix {
    Double get(Integer i, Integer j);
    void set(Integer i, Integer j, Double value);
    Integer size();
    Matrix sub(Matrix m1);
    Matrix divide_n(Double c);
}
