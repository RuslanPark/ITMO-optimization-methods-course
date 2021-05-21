package methods;

import java.util.List;

public class ConjugateGradients extends AbstractMethod {
    @Override
    List<Double> calculate() {
        writePoint(x);

        List<Double> gradient, p, ap;

        gradient = function.calculateGradient(x);
        p = multiplyByConstant(gradient, -1.0);

        double gradientNorm = function.calculateGradientNorm(x);
        while (gradientNorm >= epsilon) {
            ap = function.multiplyOnVector(p);
            alpha = gradientNorm * gradientNorm / dotProduct(ap, p);

            x = add(x, multiplyByConstant(p, alpha));
            writePoint(x);

            gradient = function.calculateGradient(x);
            double newGradientNorm = function.calculateGradientNorm(x);

            double beta = newGradientNorm * newGradientNorm / (gradientNorm * gradientNorm);
            p = subtract(multiplyByConstant(p, beta), gradient);
            gradientNorm = newGradientNorm;
        }

        return x;
    }

    private Double dotProduct(List<Double> a, List<Double> b) {
        double answer = 0.0;

        for (int i = 0; i < a.size(); i++) {
            answer += a.get(i) * b.get(i);
        }

        return answer;
    }
}
