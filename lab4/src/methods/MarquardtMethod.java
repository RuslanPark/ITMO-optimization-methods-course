package methods;

import java.util.List;

import static methods.ConjugateGradientsMethod.*;
import static methods.Vector.*;

class MarquardtMethod {
    static Vector minimize1(Function f, List<Double> x0, Double t0, Double b, Double eps) {
        Matrix h = f.getHesseMatrix();
        Vector x = new Vector(x0), negativeGradient, p, y;
        Double t;

        do {
            negativeGradient = mul_n(f.gradient(x), -1.0);
            t = t0 * b;

            do {
                t /= b;

                p = solve(h.add(ProfileMatrix.getIdentity(x0.size()).mul_n(t)), negativeGradient, x, eps);

                y = add(x, p);
            } while (f.calculate(y) > f.calculate(x));

            x = y;
            t0 = t0 * b;
        } while (p.norm() > eps);

        return x;
    }
}
