package methods;

import java.util.List;

import static methods.ConjugateGradientsMethod.*;
import static methods.Vector.*;
import static methods.OneDimensionalMethods.*;

class NewtonMethodWithSearch {
    static Vector minimize(Function f, List<Double> x0, double eps) {
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
