package model;

public class Function {
    public static double calculateFunctionValue(double x) {
        return 10 * x * Math.log(x) - x * x / 2;
    }

    public static double getLeft() {
        return 0.1;
    }

    public static double getRight() {
        return 2.5;
    }
}
