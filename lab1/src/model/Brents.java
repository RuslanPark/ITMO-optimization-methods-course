package model;

import javafx.util.Pair;
import java.util.List;

public class Brents extends CalculationMethod {
    @Override
    public double calculate() {
        double tol = epsilon;

        // Initial parameters; a < b
        double a = left, b = right;

        // Intermediate variables
        double c, d, e;
        double eps, xm, p;
        double q, r, tol1;
        double t2, u, v;
        double w, fu, fv;
        double fw, fx, x, tol3;

        c = (3.0 - Math.sqrt(5.0)) / 2;
        d = 0.0;

        eps = 1.2e-16;
        eps = Math.sqrt(eps);

        v = a + c * (b - a);
        w = v;
        x = v;
        e = 0.0;
        fx = Function.calculateFunctionValue(x);
        fv = fx;
        fw = fx;
        tol3 = tol / 3.0;

        xm = (a + b) / 2;
        tol1 = eps * Math.abs(x) + tol3;
        t2 = 2.0 * tol1;

        HashMap<String, Double> hashMap = new HashMap<>();

        while (Math.abs(x - xm) > (t2 - (b - a) / 2)) {
            p = q = r = 0.0;

            if (Math.abs(e) > tol1) {
                r = (x - w) * (fx - fv);
                q = (x - v) * (fx - fw);
                p = (x - v) * q - (x - w) * r;
                q = 2.0 * (q - r);

                if (q > 0.0) {
                    p = -p;
                } else {
                    q = -q;
                }

                r = e;
                e = d;
            }

            if ((Math.abs(p) < Math.abs(q * r / 2)) && (p > q * (a - x)) && (p < q * (b - x))) {
                // Parabolic interpolation step
                d = p / q;
                u = x + d;

                // f must not be evaluated too close to a or b
                if (((u - a) < t2) || ((b - u) < t2)) {
                    d = tol1;

                    if (x >= xm) {
                        d = -d;
                    }
                }
            } else {
                // Golden ratio step
                e = (x < xm ? b : a) - x;

                d = c * e;
            }

            // f must not be evaluated too close to x
            u = x + (Math.abs(d) >= tol1 ? d : (d > 0.0 ? tol1 : -tol1));

            fu = Function.calculateFunctionValue(u);

            // Add current step points for building graph
            hashMap.put("left", a);
            hashMap.put("right", b);
            hashMap.put("x", x);
            hashMap.put("u", u);
            graphPoints.add(hashMap);
            hashMap.clear();

            // Update a, b, v, w, and x
            if (fu <= fx) {
                (u < x ? b : a) = x;

                v = w;
                fv = fw;

                w = x;
                fw = fx;

                x = u;
                fx = fu;
            } else {
                (u < x ? a : b) = u;

                if ((fu <= fw) || (w == x)) {
                    v = w;
                    fv = fw;

                    w = u;
                    fw = fu;
                } else if ((fu > fv) && (v == x) && (v == w)) {
                    v = u;
                    fv = fu;
                }
            }

            xm = (a + b) / 2;
            tol1 = eps * Math.abs(x) + tol3;
            t2 = 2.0 * tol1;
        }

        return x;
    }
}