package methods;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MatrixGenerator {
    private ProfileMatrix task2Matrix;
    double task2Sum;
    private List<Double> task2F;
    public void task2Generate(String directory, int n, int k) {
        List<Integer> x = new ArrayList<>();
        for (int i = 1; i <= n; ++i) {
            x.add(i);
        }

        Random random = new Random();
        double sum = 0.0;
        List<List<Double>> matrix = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            matrix.add(new ArrayList<>(Collections.nCopies(n, 0.0)));
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                double randomNum = random.nextInt(5) - 4;
                sum += randomNum;
                matrix.get(i).set(j, randomNum);
                if (randomNum == 0) {
                    matrix.get(j).set(i, randomNum);
                } else {
                    while (true) {
                        randomNum = random.nextInt(5) - 4;
                        if (randomNum != 0) {
                            matrix.get(j).set(i, randomNum);
                            break;
                        }
                    }
                }
                sum += randomNum;
            }
        }

        sum = -sum;
        task2Sum = sum;

        matrix.get(0).set(0, sum + Math.pow(10, -k));
        for (int i = 1; i < matrix.size(); ++i) {
            matrix.get(i).set(i, sum);
        }
        task2Matrix = new ProfileMatrix(matrix);
        task2Matrix.writeMatrix(directory);

        task2F = multiplyMatrixOnVector(matrix, x);
        Util.writeFile(directory, "f.txt", task2F);
    }

    public void task2Regenerate(int k, String directory) {
        double oldValue = task2Matrix.get(0, 0);
        task2Matrix.set(0, 0, task2Sum + Math.pow(10, -k));
        task2Matrix.writeMatrix(directory);
        task2F.set(0, task2F.get(0) - oldValue + task2Matrix.get(0, 0));
        Util.writeFile(directory, "f.txt", task2F);
    }

    private List<Double> multiplyMatrixOnVector(List<List<Double>> matrix, List<Integer> vector) {
        List<Double> res = new ArrayList<>();
        for (int i = 0; i < matrix.size(); ++i) {
            double sum = 0;
            for (int j = 0; j < matrix.size(); ++j) {
                sum += matrix.get(i).get(j) * vector.get(j);
            }
            res.add(sum);
        }
        return res;
    }

    public void task3Generate(String directory, int n) {
        List<Integer> x = new ArrayList<>();
        for (int i = 1; i <= n; ++i) {
            x.add(i);
        }

        List<List<Double>> matrix = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            matrix.add(new ArrayList<>(Collections.nCopies(n, 0.0)));
        }
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= i; ++j) {
                matrix.get(i - 1).set(j - 1, 1.0 / (i + j - 1));
                matrix.get(j - 1).set(i - 1, 1.0 / (i + j - 1));
            }
        }
        ProfileMatrix profileMatrix = new ProfileMatrix(matrix);
        profileMatrix.writeMatrix(directory);

        Path path = Path.of(directory);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            System.err.println("Can't create directory");
            return;
        }
        Util.writeFile(directory, "f.txt", multiplyMatrixOnVector(matrix, x));
    }

    public void task4Generate(String directory, int n) {
        List<Integer> x = new ArrayList<>();
        for (int i = 1; i <= n; ++i) {
            x.add(i);
        }

        List<List<Double>> matrix = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            matrix.add(new ArrayList<>(Collections.nCopies(n, 0.0)));
        }

        Random random = new Random();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix.get(i).set(j, random.nextDouble() + 1);
            }
        }
        ProfileMatrix profileMatrix = new ProfileMatrix(matrix);
        profileMatrix.writeMatrix(directory);

        Util.writeFile(directory, "f.txt", multiplyMatrixOnVector(matrix, x));
    }

    public SparseRowColumnMatrix task5Generate(String directory, int n) {
        List<Integer> x = new ArrayList<>();
        for (int i = 1; i <= n; ++i) {
            x.add(i);
        }

        List<List<Double>> matrix = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            matrix.add(new ArrayList<>(Collections.nCopies(n, 0.0)));
        }

        Random random = new Random();
        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                matrix.get(i).set(j, random.nextDouble() + 1);
                matrix.get(j).set(i, matrix.get(i).get(j));
            }
        }
        SparseRowColumnMatrix sparseRowColumnMatrix = new SparseRowColumnMatrix(matrix);
        return sparseRowColumnMatrix;
        /*sparseRowColumnMatrix.writeMatrix(directory);

        Util.writeFile(directory, "f.txt", multiplyMatrixOnVector(matrix, x));*/
    }

    public SparseRowColumnMatrix task6Generate(String directory, int n) {
        List<Integer> x = new ArrayList<>();
        for (int i = 1; i <= n; ++i) {
            x.add(i);
        }

        List<List<Double>> matrix = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            matrix.add(new ArrayList<>(Collections.nCopies(n, 0.0)));
        }


        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix.get(i).set(j, (double) (1 / (i + j + 1)));
            }
        }
        SparseRowColumnMatrix sparseRowColumnMatrix = new SparseRowColumnMatrix(matrix);
        return sparseRowColumnMatrix;
        /*sparseRowColumnMatrix.writeMatrix(directory);

        Util.writeFile(directory, "f.txt", multiplyMatrixOnVector(matrix, x));*/
    }
}
