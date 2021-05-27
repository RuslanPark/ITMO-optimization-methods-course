package methods;

import java.util.List;

import static methods.ConjugateGradientsMethod.*;
import static methods.OneDimensionalMethods.*;
import static methods.Vector.*;

class NewtonMethodWithDescent {
    static Vector minimize(Function f, List<Double> x0, double eps) {
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
}
