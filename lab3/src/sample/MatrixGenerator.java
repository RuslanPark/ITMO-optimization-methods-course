package sample;

import javax.xml.stream.events.Characters;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MatrixGenerator {
    public static void task2Generator(String directory, int n, int k) {
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
        List<Double> di = new ArrayList<>();
        di.add(sum + Math.pow(10, -k));
        matrix.get(0).set(0, di.get(0));
        for (int i = 1; i < n; ++i) {
            matrix.get(i).set(i, sum);
            di.add(sum);
        }

        List<Double> al = new ArrayList<>();
        List<Double> au = new ArrayList<>();
        List<Integer> ia = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            boolean wasInL = false;
            ia.add(al.size());
            for (int j = 0; j < i; ++j) {
                if (matrix.get(i).get(j) != 0 || wasInL) {
                    wasInL = true;
                    al.add(matrix.get(i).get(j));
                    au.add(matrix.get(j).get(i));
                }
            }
        }
        ia.add(al.size());

        Path path = Path.of(directory);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            System.err.println("Can't create directory");
            return;
        }
        writeFile(directory, "di.txt", di);
        writeFile(directory, "al.txt", al);
        writeFile(directory, "au.txt", au);
        writeFile(directory, "ia.txt", ia);
        writeFile(directory, "f.txt", multiplyMatrixOnVector(matrix, x));
        writeMatrix(directory, "matrix.txt", matrix);//For debug only
    }

    private static void writeMatrix(String directory, String filename, List<List<Double>> matrix) {//Only for debug
        Path filePath = Path.of(directory, filename);
        try(final BufferedWriter fileWriter = Files.newBufferedWriter(filePath)) {
            for (int i = 0; i < matrix.size(); ++i) {
                for (int j = 0; j < matrix.size(); ++j) {
                    fileWriter.write(matrix.get(i).get(j) + " ");
                }
                fileWriter.write(String.format("%n"));
            }
        } catch (IOException e) {
            System.err.println("Error while writing file " + filename);
        }
    }

    private static <T> void writeFile(String directory, String filename, List<T> list) {
        Path filePath = Path.of(directory, filename);
        try(final BufferedWriter fileWriter = Files.newBufferedWriter(filePath)) {
            for (int i = 0; i < list.size(); ++i) {
                fileWriter.write(list.get(i) + " ");
            }
        } catch (IOException e) {
            System.err.println("Error while writing file " + filename);
        }
    }

    private static List<Double> multiplyMatrixOnVector(List<List<Double>> matrix, List<Integer> vector) {
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

    public static void task3Generate(String directory, int n) {
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
        List<Double> di = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            di.add(matrix.get(i).get(i));
        }

        List<Double> al = new ArrayList<>();
        List<Double> au = new ArrayList<>();
        List<Integer> ia = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            ia.add(al.size() + 1);
            for (int j = 0; j < i; ++j) {
                al.add(matrix.get(i).get(j));
                au.add(matrix.get(j).get(i));
            }
        }
        ia.add(al.size() + 1);

        Path path = Path.of(directory);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            System.err.println("Can't create directory");
            return;
        }
        writeFile(directory, "di.txt", di);
        writeFile(directory, "al.txt", al);
        writeFile(directory, "au.txt", au);
        writeFile(directory, "ia.txt", ia);
        writeFile(directory, "f.txt", multiplyMatrixOnVector(matrix, x));
        writeMatrix(directory, "matrix.txt", matrix);//For debug only
    }
}
