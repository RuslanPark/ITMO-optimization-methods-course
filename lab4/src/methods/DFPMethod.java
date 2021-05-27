package methods;

import java.util.List;

import static methods.OneDimensionalMethods.*;
import static methods.Vector.*;

class DFPMethod {
    static Vector minimize(Function f, List<Double> x0, double eps) {
        Matrix g = ProfileMatrix.getIdentity(x0.size());
        Vector x = new Vector(x0), negativeGradient = mul_n(f.gradient(x), -1.0), p, oldX, oldGradient, dp, dx, v;

        Double a;

        Vector finalX1 = x;
        Vector finalP1 = negativeGradient;

        a = dichotomy(c-> f.calculate(add(finalX1, mul_n(finalP1, c))));

        oldX = x;
        x = add(x, mul_n(negativeGradient, a));
        dx = sub(x, oldX);

        do {
            oldGradient = negativeGradient;
            negativeGradient = mul_n(f.gradient(x), -1.0);
            dp = sub(negativeGradient, oldGradient);

            v = dp.product(g);

            g = g.sub(dx.selfProduct().divide_n(dotProduct(dp, dx))).sub(v.selfProduct().divide_n(dotProduct(v, dp)));

            p = negativeGradient.product(g);

            Vector finalP = p;
            Vector finalX = x;

            a = dichotomy(c-> f.calculate(add(finalX, mul_n(finalP, c))));

            oldX = x;
            x = add(x, mul_n(p, a));
            dx = sub(x, oldX);
        } while (dx.norm() > eps);

        return x;
    }
}
