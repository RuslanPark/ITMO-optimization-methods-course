package methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	    List<List<Double>> a = new ArrayList<>(Arrays.asList(
	            new ArrayList<>(Arrays.asList(20.0, 3.0)),
                new ArrayList<>(Arrays.asList(3.0, 14.0))));

	    List<Double> b = new ArrayList<>(Arrays.asList(6.0, 1.0));

	    Double c = 0.0;

	    Function f = new QuadraticFunction(a, b, c);
	    List<Double> x = new ArrayList<>(Arrays.asList(1.0, 1.0));

	    System.out.println(NewtonMethod.minimize(f, x, 0.0001));
		System.out.println(NewtonMethod.minimizeWithSearch(f, x, 0.0001));
		System.out.println(NewtonMethod.minimizeWithDescent(f, x, 0.0001));
		System.out.println(DFPMethod.minimize(f, x, 0.0001));
        System.out.println(PowellMethod.minimize(f, x, 0.0001));
        System.out.println(MarquardtMethod.minimize1(f, x,1000.0, 0.5, 0.0001));
    }
}
