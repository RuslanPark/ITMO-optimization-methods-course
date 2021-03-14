package model;

import javafx.util.Pair;
import java.util.List;

public class Brents extends CalculationMethod {
    private final double tol = 0.0000001;

    @Override
    public double calculate() {

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
                if (x < xm) {
                    e = b - x;
                } else {
                    e = a - x;
                }
                d = c * e;
            }

            // f must not be evaluated too close to x
            if (Math.abs(d) >= tol1) {
                u = x + d;
            } else {
                if (d > 0.0) {
                    u = x + tol1;
                } else {
                    u = x - tol1;
                }
            }

            fu = Function.calculateFunctionValue(u);

            // Update a, b, v, w, and x
            if (fx <= fu) {
                if (u < x) {
                    a = u;
                } else {
                    b = u;
                }
            }

            if (fu <= fx) {
                if (u < x) {
                    b = x;
                } else {
                    a = x;
                }
                v = w;
                fv = fw;
                w = x;
                fw = fx;
                x = u;
                fx = fu;
            } else {
                if ((fu <= fw) || (w == x)) {
                    v = w;
                    fv = fw;
                    w = u;
                    fw = fu;
                } else if ((fu > fv) && (v != x) && (v != w)) {
                } else {
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