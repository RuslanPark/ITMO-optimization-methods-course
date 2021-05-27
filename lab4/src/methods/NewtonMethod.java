package methods;

import java.util.List;

import static methods.ConjugateGradientsMethod.*;
import static methods.OneDimensionalMethods.*;
import static methods.Vector.*;

class NewtonMethod {
    static Vector minimize(Function f, List<Double> x0, Double eps) {
        Matrix h = f.getHesseMatrix();
        Vector x = new Vector(x0), negativeGradient, p;

        do {
            negativeGradient = mul_n(f.gradient(x), -1.0);
            p = solve(h, negativeGradient, x, eps);

            x = add(x, p);
        } while (p.norm() > eps);

        return x;
    }

    static Vector minimizeWithDescent(Function f, List<Double> x0, Double eps) {
        Matrix h = f.getHesseMatrix();
        Vector x = new Vector(x0), negativeGradient = mul_n(f.gradient(x), -1.0), p = negativeGradient, oldX;

        Double a;

        Vector finalX1 = x;
        Vector finalP1 = p;

        a = dichotomy(c-> f.calculate(add(finalX1, mul_n(finalP1, c))));

        oldX = x;
        x = add(x, mul_n(p, a));

        while (sub(x, oldX).norm() > eps) {
            negativeGradient = mul_n(f.gradient(x), -1.0);
            p = solve(h, negativeGradient, x, eps);

            if (dotProduct(p, negativeGradient) < 0) {
                p = negativeGradient;
            }

            Vector finalX = x;
            Vector finalP = p;

            a = dichotomy(c-> f.calculate(add(finalX, mul_n(finalP, c))));

            oldX = x;
            x = add(x, mul_n(p, a));
        }

        return x;
    }

    static Vector minimizeWithSearch(Function f, List<Double> x0, Double eps) {
        Matrix h = f.getHesseMatrix();
        Vector x = new Vector(x0), negativeGradient, p, oldX;

        Double a;

        do {
            negativeGradient = mul_n(f.gradient(x), -1.0);
            p = solve(h, negativeGradient, x, eps);

            Vector finalP = p;
            Vector finalX = x;

            a = dichotomy(c-> f.calculate(add(finalX, mul_n(finalP, c))));

            oldX = x;
            x = add(x, mul_n(p, a));
        } while (sub(x, oldX).norm() > eps);

        return x;
    }
}
