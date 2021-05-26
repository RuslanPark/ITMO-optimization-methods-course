package methods;

import java.util.List;

import static methods.ConjugateGradientsMethod.*;
import static methods.VectorOperations.*;

class NewtonMethod {
    static List<Double> minimize(Function f, List<Double> x, double eps) {
        ProfileMatrix h = new ProfileMatrix(f.getHesseMatrix());
        List<Double> negativeGradient, p;

        do {
            negativeGradient = mul_n(f.gradient(x), -1);
            p = solve(h, negativeGradient, x, eps);

            x = add(x, p);
        } while (norm(p) > eps);

        return x;

    }
}
