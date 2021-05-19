package methods;

import java.util.List;

public class GaussMethod {
    public static void calculate(String directory) {
        ProfileMatrix matrix = new ProfileMatrix(directory);
        List<Double> f = Util.readFile(directory, "f.txt");

        for (int k = 0; k < matrix.size(); ++k) {//Forward stroke
            double max = Math.abs(matrix.get(k, k));
            int maxRow = k;
            for (int i = k + 1; i < matrix.size(); ++i) {//Find main element
                double value = Math.abs(matrix.get(i, k));
                if (value > max) {
                    max = value;
                    maxRow = i;
                }
            }
            double temp = f.get(k);//Swap rows
            f.set(k, f.get(maxRow));
            f.set(maxRow, temp);
            matrix.swap(k, maxRow);

            for (int i = k + 1; i < matrix.size(); ++i) {
                double coefficient = matrix.get(i, k) / matrix.get(k, k);
                f.set(i, f.get(i) - coefficient * f.get(k));
                for (int j = k; j < matrix.size(); ++j) {
                    matrix.set(i, j, matrix.get(i, j) - coefficient * matrix.get(k, j));
                }
            }
        }

        f.set(f.size() - 1, f.get(f.size() - 1) / matrix.get(f.size() - 1, f.size() - 1));
        for (int k = matrix.size() - 2; k >= 0; --k) {//Reverse motion
            double sum = f.get(k);
            for (int j = k + 1; j < matrix.size(); ++j) {
                sum -= matrix.get(k, j) * f.get(j);
            }
            f.set(k, sum / matrix.get(k, k));
        }

        Util.writeFile(directory, "res.txt", f);
    }
}
