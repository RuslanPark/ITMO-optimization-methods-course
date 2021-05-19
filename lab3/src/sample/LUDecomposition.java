package sample;
import java.util.List;

public class LUDecomposition {
    public static void calculate(String directory) {
        ProfileMatrix matrix = new ProfileMatrix(directory);//Read matrix
        List<Double> f = Util.readFile(directory, "f.txt");//Read right vector

        for (int i = 1; i < matrix.size(); ++i) {//Create matrix L and U and write them in the same place
            for (int j = 0; j < i; ++j) {//Fill L_ij
                double sum = 0;
                for (int k = 0; k < j; ++k) {
                    sum += matrix.get(i, k) * matrix.get(k, j);
                }
                matrix.set(i, j, matrix.get(i, j) - sum);
            }

            for (int j = 0; j < i; ++j) {//Fill U_ji
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
            matrix.set(i, i, matrix.get(i, i) - sum);//Fill L_ii
        }

        for (int i = 0; i < f.size(); ++i) {//Forward stroke
            double sum = 0;
            for (int k = 0; k < i; ++k) {
                sum += matrix.get(i, k) * f.get(k);
            }
            f.set(i, (f.get(i) - sum) / matrix.get(i, i));
        }

        for (int i = f.size() - 1; i >= 0; --i) {//Reverse motion
            double sum = 0;
            for (int k = i + 1; k < f.size(); ++k) {
                sum += matrix.get(i, k) * f.get(k);
            }
            f.set(i, f.get(i) - sum);
        }

        Util.writeFile(directory, "res.txt", f);//Write result if file
    }
}
