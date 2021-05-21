package methods;

import java.util.List;

public class SteepestDescent extends AbstractMethod {
    @Override
    List<Double> calculate() {
        writePoint(x);

        while (function.calculateGradientNorm(x) >= epsilon) {
            List<Double> functionGradient = function.calculateGradient(x);
            alpha = OneDimensionalMethods.parabolas(c->
                         function.calculateValue(subtract(x, multiplyByConstant(functionGradient, c)))
                    );

            x = subtract(x, multiplyByConstant(functionGradient,
                    alpha));

            writePoint(x);
        }

        return x;
    }
}
