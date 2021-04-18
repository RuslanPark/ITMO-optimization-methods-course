package methods;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class OneDimensionalMethods {
    private static final double left = 0.0;
    private static final double right = 1.0;

    private static final double epsilon = 0.0001;

    public static double dichotomy(Function<Double, Double> f) {
        double delta = epsilon / 100;

        double l = left, r = right;

        while ((r - l) / 2 > epsilon) {
            // Calculate X1 and X2
            double x1 = (l + r- delta) / 2;
            double x2 = (l + r + delta) / 2;

            // Check the condition and init new segment
            if (f.apply(x1) <= f.apply(x2)) {
                r = x2;
            } else {
                l = x1;
            }
        }

        return (l + r) / 2;
    }

    public static double goldenRatio(Function<Double, Double> f) {
        // Init constant for golden ratio
        final double phi = 0.61803;

        // Init x1, x2 and function values in these points
        double x1 = right - phi * (right - left);
        double x2 = left + phi * (right - left);
        double y1 = f.apply(x1);
        double y2 = f.apply(x2);

        double l = left, r = right;

        // Calculate until get the required accuracy
        while ((r - l) / 2 > epsilon) {
            // Checking at which point the function is smaller and init new segment
            if (y1 <= y2) {
                r = x2;
                x2 = x1;
                x1 = r - phi * (r - l);
                y2 = y1;
                y1 = f.apply(x1);
            } else {
                l = x1;
                x1 = x2;
                x2 = l + phi * (r - l);
                y1 = y2;
                y2 = f.apply(x2);
            }
        }

        return (l + r) / 2;
    }

    public static double fibonacci(Function<Double, Double> f) {
        int n = 0;
        final List<Long> fibNumbs = new ArrayList<>();

        // Add first and second fibonacci numbers
        fibNumbs.add(1L);
        fibNumbs.add(1L);
        n = 2;

        // Init limit for fibonacci numbers
        double lim = (right - left) / epsilon;

        // Calculate fibonacci numbers
        while (lim >= fibNumbs.get(fibNumbs.size() - 1)) {
            fibNumbs.add(fibNumbs.get(fibNumbs.size() - 1) + fibNumbs.get(fibNumbs.size() - 2));
            n++;
        }

        double x1 = left + (right - left) * ((double) fibNumbs.get(n - 3) / fibNumbs.get(n - 1));
        double x2 = left + (right - left) * ((double) fibNumbs.get(n - 2) / fibNumbs.get(n - 1));
        double y1 = f.apply(x1);
        double y2 = f.apply(x2);

        double l = left, r = right;

        for (int i = 1; i < n - 2; ++i) {
            // Checking at which point the function is smaller and init new segment
            if (y1 > y2) {
                l= x1;
                x1 = x2;
                x2 = l + (r - l) * ((double) fibNumbs.get(n - i - 2) / fibNumbs.get(n - i - 1));
                y1 = y2;
                y2 = f.apply(x2);
            } else {
                r = x2;
                x2 = x1;
                x1 = l + (r - l) * ((double) fibNumbs.get(n - i - 3) / fibNumbs.get(n - i - 1));
                y2 = y1;
                y1 = f.apply(x1);
            }
        }

        return (l + r) / 2;
    }
    
    public static double parabolas(Function<Double, Double> f) {
        // Initial parameters; x1 < x2 < x3
        double x1 = left, x2 = 0.5, x3 = right;

        // Calculate function value in x1, x2, x3
        double f1 = f.apply(x1);
        double f2 = f.apply(x2);
        double f3 = f.apply(x3);

        // Init x_i-1, x_i and check for firstIteration
        double xPrev = 0;
        double x;
        boolean firstIteration = true;

        while (true) {
            // Calculate parabola coefficients
            double a0 = f1;
            double a1 = (f2 - f1) / (x2 - x1);
            double a2 = (((f3 - f1) / (x3 - x1)) - ((f2 - f1) / (x2 - x1))) / (x3 - x2);

            x = (x1 + x2 - a1 / a2) / 2;

            double fx = f.apply(x);

            // Checking distance between points on all iterations except the first one
            if (firstIteration) {
                firstIteration = false;
            } else if (Math.abs(x - xPrev) < epsilon) {
                return x;
            }
            xPrev = x;

            // Init new points
            if (x1 < x && x < x2 && x2 < x3) {
                if (fx >= f2) {
                    x1 = x;
                    f1 = fx;
                } else {
                    x3 = x2;
                    f3 = f2;
                    x2 = x;
                    f2 = fx;
                }
            } else if (x1 < x2 && x2 < x && x < x3) {
                if (f2 >= fx) {
                    x1 = x2;
                    f1 = f2;
                    x2 = x;
                    f2 = fx;
                } else {
                    x3 = x;
                    f3 = fx;
                }
            }
        }
    }

    public static double brents(Function<Double, Double> f) {
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
        fx = f.apply(x);
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
                e = (x < xm ? b : a) - x;

                d = c * e;
            }

            // f must not be evaluated too close to x
            u = x + (Math.abs(d) >= tol1 ? d : (d > 0.0 ? tol1 : -tol1));

            fu = f.apply(u);

            // Update a, b, v, w, and x
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
                if (u < x) {
                    a = u;
                } else {
                    b = u;
                }

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
