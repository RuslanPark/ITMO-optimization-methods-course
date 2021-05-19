package methods;

import java.util.List;

import static methods.MatrixAndVectorOperations.*;

public class ConjugateGradientsMethod {
    static List<Double> solve(Matrix a, List<Double> b, List<Double> x, double eps) {
        final int n = b.size();

        double alpha;

        List<Double> p, r, w, y;

        r = product(a, x);
        r = sub(b, r);
        p = r;

        double rsold = dotProduct(r, r);

        while (true) {
            w = product(a, p);

            alpha = rsold / dotProduct(p, w);

            y = mul_n(p, alpha);
            x = add(x, y);

            y = mul_n(w, alpha);
            r = sub(r, y);

            double rsnew = dotProduct(r, r);

            if (rsnew < eps) {
                return x;
            }

            y = mul_n(p, rsnew / rsold);
            p = add(r, y);

            rsold = rsnew;
        }
    }
}
