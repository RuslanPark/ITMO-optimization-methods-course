package methods;

import java.util.ArrayList;
import java.util.List;

public class ConjugateGradients extends AbstractMethod {
    @Override
    List<Double> calculate() {
        points.add(x);

        List<Double> gradient = function.calculateGradient(x);
        List<Double> p = multiplyByConstant(gradient, -1.0), t, ap;

        Double a;

        while (function.calculateGradientNorm(x) >= epsilon) {
            ap = product(function.getMatrix(), p);

            a = dotProduct(gradient, gradient) / dotProduct(ap, p);

            x = add(x, multiplyByConstant(p, a));
            points.add(x);

            t = gradient;
            gradient = add(gradient, multiplyByConstant(ap, a));

            p = subtract(multiplyByConstant(p, dotProduct(gradient, gradient) / dotProduct(t, t)), gradient);
        }

        return x;
    }

    public List<Double> product(List<List<Double>> matrix, List<Double> vector) {
        List<Double> answer = new ArrayList<>();

        Double t;

        for (int i = 1; i < matrix.size(); i++)
        {
            t = 0.0;

            for (int j = 1; j < matrix.size(); j++)
            {
                t += matrix.get(i).get(j) * vector.get(j - 1);
            }
            answer.add(t);
        }

        return answer;
    }

    private Double dotProduct(List<Double> a, List<Double> b) {
        Double answer = 0.0;

        for (int i = 0; i < a.size(); i++) {
            answer += a.get(i) * b.get(i);
        }

        return answer;
    }
}
