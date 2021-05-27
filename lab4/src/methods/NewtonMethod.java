package methods;

import java.util.List;

import static methods.ConjugateGradientsMethod.*;
import static methods.Vector.*;

class NewtonMethod {
    static Vector minimize(Function f, List<Double> x0, double eps) {
        Matrix h = f.getHesseMatrix();
        Vector x = new Vector(x0), negativeGradient, p;

        do {
            negativeGradient = mul_n(f.gradient(x), -1.0);
            p = solve(h, negativeGradient, x, eps);

            x = add(x, p);
        } while (p.norm() > eps);

        return x;
    }
}
