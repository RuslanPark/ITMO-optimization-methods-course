package methods;

import java.util.List;

public class SteepestDescent extends AbstractMethod {
    @Override
    List<Double> calculate() {
        points.add(x);

        while (function.calculateGradientNorm(x) >= epsilon) {
            alpha = OneDimensionalMethods.dichotomy(c->{
                return function.calculateValue(subtract(x, multiplyByConstant(function.calculateGradient(x), c)));
                    });

            x = subtract(x, multiplyByConstant(function.calculateGradient(x),
                    alpha));

            points.add(x);
        }

        return x;
    }
}
