package methods;

import java.util.ArrayList;
import java.util.List;

public class ConjugateGradients extends AbstractMethod {
    @Override
    List<Double> calculate() {
        writePoint(x);

        List<Double> gradient, p, ap;

        gradient = add(function.multiplyOnVector(x), function.getB());
        p = multiplyByConstant(gradient, -1.0);

        double gradientNorm = 0.0;
        for (Double aDouble : gradient) {
            gradientNorm += aDouble * aDouble;
        }
        gradientNorm = Math.sqrt(gradientNorm);
        while (gradientNorm >= epsilon) {
            ap = function.multiplyOnVector(p);
            alpha = gradientNorm * gradientNorm / dotProduct(ap, p);

            x = add(x, multiplyByConstant(p, alpha));
            writePoint(x);

            List<Double> newGradient = add(gradient, multiplyByConstant(ap, alpha));
            double newGradientNorm = 0.0;
            for (Double aDouble : newGradient) {
                newGradientNorm += aDouble * aDouble;
            }
            newGradientNorm = Math.sqrt(newGradientNorm);

            double beta = newGradientNorm * newGradientNorm / (gradientNorm * gradientNorm);
            p = subtract(multiplyByConstant(p, beta), newGradient);
            gradient = newGradient;
            gradientNorm = newGradientNorm;
        }

        return x;
    }

    private Double dotProduct(List<Double> a, List<Double> b) {
        Double answer = 0.0;

        for (int i = 0; i < a.size(); i++) {
            answer += a.get(i) * b.get(i);
        }

        return answer;
    }
}
