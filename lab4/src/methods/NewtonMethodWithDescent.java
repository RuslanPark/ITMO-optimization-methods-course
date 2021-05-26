package methods;

import java.util.List;

import static methods.ConjugateGradientsMethod.*;
import static methods.OneDimensionalMethods.*;
import static methods.VectorOperations.*;

class NewtonMethodWithDescent {
    static List<Double> minimize(Function f, List<Double> x, double eps) {
        ProfileMatrix h = new ProfileMatrix(f.getHesseMatrix());
        List<Double> negativeGradient = mul_n(f.gradient(x), -1), p = negativeGradient, oldX;

        Double a;

        List<Double> finalX1 = x;
        List<Double> finalP1 = p;

        a = dichotomy(c->{
            return f.calculate(add(finalX1, mul_n(finalP1, c)));
        });

        oldX = x;
        x = add(x, mul_n(p, a));

        while (norm(sub(x, oldX)) > eps) {
            negativeGradient = mul_n(f.gradient(x), -1);
            p = solve(h, negativeGradient, x, eps);

            if (dotProduct(p, negativeGradient) < 0) {
                p = negativeGradient;
            }

            List<Double> finalX = x;
            List<Double> finalP = p;

            a = dichotomy(c-> f.calculate(add(finalX, mul_n(finalP, c))));

            oldX = x;
            x = add(x, mul_n(p, a));
        }

        return x;
    }
}
