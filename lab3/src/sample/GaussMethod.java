package sample;

import java.util.List;

public class GaussMethod {
    public void calculate(String directory) {
        ProffilMatrix matrix = new ProffilMatrix();
        matrix.readMatrix(directory);
        List<Double> f = Util.readFile(directory, "f.txt");

        matrix.printMatrix();
        System.out.println();
        for (int k = 0; k < matrix.size(); ++k) {
            double max = matrix.get(k, k);
            int maxRow = k;
            for (int i = k + 1; i < matrix.size(); ++i) {
                double value = matrix.get(i, k);
                if (value > max) {
                    max = value;
                    maxRow = i;
                }
            }
            matrix.swap(k, maxRow);

            for (int i = k + 1; i < matrix.size(); ++i) {
                double coef = matrix.get(i, k) / matrix.get(k, k);
                f.set(i, f.get(i) - coef * f.get(k));
                for (int j = k; j < matrix.size(); ++j) {
                    matrix.set(i, j, matrix.get(i, j) - coef * matrix.get(k, j));
                }
            }
        }

        f.set(f.size() - 1, f.get(f.size() - 1) / matrix.get(f.size() - 1, f.size() - 1));
        for (int k = matrix.size() - 2; k >= 0; --k) {
            double sum = f.get(k);
            for (int j = k + 1; j < matrix.size(); ++j) {
                sum -= matrix.get(k, j) * f.get(j);
            }
            f.set(k, sum / matrix.get(k, k));
        }

        Util.writeFile(directory, "res.txt", f);
    }
}
