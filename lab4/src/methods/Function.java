package methods;

public interface Function {
    Double calculate(Vector x);
    Vector gradient(Vector x);
    Matrix getHesseMatrix();
}
