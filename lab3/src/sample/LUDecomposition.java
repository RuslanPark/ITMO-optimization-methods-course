package sample;
import java.util.List;

public class LUDecomposition {
    public static void calculate(String directory) {
        ProfileMatrix matrix = new ProfileMatrix(directory);
        List<Double> f = Util.readFile(directory, "f.txt");

        for (int i = 1; i < matrix.size(); ++i) {
            for (int j = 0; j < i; ++j) {
                double sum = 0;
                for (int k = 0; k < j; ++k) {
                    sum += matrix.get(i, k) * matrix.get(k, j);
                }
                matrix.set(i, j, matrix.get(i, j) - sum);
            }

            for (int j = 0; j < i; ++j) {
                double sum = 0;
                for (int k = 0; k < j; ++k) {
                    sum += matrix.get(j, k) * matrix.get(k, i);
                }
                matrix.set(j, i, (matrix.get(j, i) - sum) / matrix.get(j, j));
            }

            double sum = 0;
            for (int k = 0; k < i; ++k) {
                sum += matrix.get(i, k) * matrix.get(k, i);
            }
            matrix.set(i, i, matrix.get(i, i) - sum);
        }

        for (int i = 0; i < f.size(); ++i) {
            double sum = f.get(i);
            for (int k = 0; k < i; ++k) {
                sum -= matrix.get(i, k) * f.get(k);
            }
            f.set(i, sum / matrix.get(i, i));
        }

        for (int i = f.size() - 1; i >= 0; --i) {
            double sum = f.get(i);
            for (int k = i + 1; k < f.size(); ++k) {
                sum -= matrix.get(i, k) * f.get(k);
            }
            f.set(i, sum);
        }

        Util.writeFile(directory, "res.txt", f);
    }
}
