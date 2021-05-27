package methods;

import static methods.Vector.*;

class ConjugateGradientsMethod {
    static Vector solve(Matrix a, Vector b, Vector x, Double eps) {
        Double alpha;

        Vector p, r, w, y;

        r = x.product(a);
        r = sub(b, r);
        p = r;

        Double rsold = dotProduct(r, r);

        while (true) {
            w = p.product(a);

            alpha = rsold / dotProduct(p, w);

            y = mul_n(p, alpha);
            x = add(x, y);

            y = mul_n(w, alpha);
            r = sub(r, y);

            Double rsnew = dotProduct(r, r);

            if (rsnew < eps) {
                return x;
            }

            y = mul_n(p, rsnew / rsold);
            p = add(r, y);

            rsold = rsnew;
        }
    }
}
