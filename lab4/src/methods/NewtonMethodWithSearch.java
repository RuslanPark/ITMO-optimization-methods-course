package methods;

import java.util.List;

import static methods.ConjugateGradientsMethod.*;
import static methods.VectorOperations.*;
import static methods.OneDimensionalMethods.*;

class NewtonMethodWithSearch {
    static List<Double> minimize(Function f, List<Double> x, double eps) {
        ProfileMatrix h = new ProfileMatrix(f.getHesseMatrix());
        List<Double> negativeGradient, p, oldX = x;

        Double a;

        do {
            negativeGradient = mul_n(f.gradient(x), -1);
            p = solve(h, negativeGradient, x, eps);

            List<Double> finalP = p;
            List<Double> finalX = x;

            a = dichotomy(c-> f.calculate(add(finalX, mul_n(finalP, c))));

            oldX = x;
            x = add(x, mul_n(p, a));
        } while (norm(sub(x, oldX)) > eps);

        return x;
    }
}
