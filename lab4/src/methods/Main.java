package methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	    List<List<Double>> a = new ArrayList<>(Arrays.asList(
	            new ArrayList<>(Arrays.asList(2.0, -1.2)),
                new ArrayList<>(Arrays.asList(-1.2, 2.0))));

	    List<Double> b = new ArrayList<>(Arrays.asList(0.0, 0.0));

	    Double c = 0.0;

	    Function f = new QuadraticFunction(a, b, c);
	    List<Double> x = new ArrayList<>(Arrays.asList(4.0, 1.0));

	    System.out.println(NewtonMethod.minimize(f, x, 0.0001));
		System.out.println(NewtonMethodWithSearch.minimize(f, x, 0.0001));
		System.out.println(NewtonMethodWithDescent.minimize(f, x, 0.0001));
    }
}
