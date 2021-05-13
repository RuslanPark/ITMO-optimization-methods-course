package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LUDecomposition {
    public void calculate(String directory) {
        List<Double> di = readFile(directory, "di.txt");
        List<Double> al = readFile(directory, "al.txt");
        List<Double> au = readFile(directory, "au.txt");
        List<Integer> ia = readFileWithInt(directory, "ia.txt");
        List<Double> f = readFile(directory, "f.txt");

        for (int i = 1; i < di.size(); ++i) {
            for (int j = i - ia.get(i + 1) + ia.get(i), step = 0; j < i; ++j, ++step) {
                double sum = 0;
                int lk = i - ia.get(i + 1) + ia.get(i);
                int uk = j - ia.get(j + 1) + ia.get(j);
                int lInd = ia.get(i);
                int uInd = ia.get(j);
                if (lk > uk) {
                    uInd += lk - uk;
                } else {
                    lInd += uk - lk;
                }
                for (int k = Math.max(lk, uk); k < j; ++k, lInd++, uInd++) {
                    sum += al.get(lInd) * au.get(uInd);
                }

                al.set(ia.get(i) + step, al.get(ia.get(i) + step) - sum);
            }

            for (int j = i - ia.get(i + 1) + ia.get(i), step = 0; j < i; ++j, ++step) {
                double sum = 0;
                int lk = j - ia.get(j + 1) + ia.get(j);
                int uk = i - ia.get(i + 1) + ia.get(i);
                int lInd = ia.get(j);
                int uInd = ia.get(i);
                if (lk > uk) {
                    uInd += lk - uk;
                } else {
                    lInd += uk - lk;
                }
                for (int k = Math.max(lk, uk); k < j; ++k, lInd++, uInd++) {
                    sum += al.get(lInd) * au.get(uInd);
                }

                au.set(ia.get(i) + step, (au.get(ia.get(i) + step) - sum) / di.get(j));
            }

            double sum = 0;
            int ind = ia.get(i);
            for (int k = i - ia.get(i + 1) + ia.get(i); k < i; ++k, ind++) {
                sum += al.get(ind) * au.get(ind);
            }
            di.set(i, di.get(i) - sum);
        }

        List<List<Double>> matrix = new ArrayList<>();//Only for debug
        for (int i = 0; i < di.size(); ++i) {
            matrix.add(new ArrayList<>(Collections.nCopies(di.size(), 0.0)));
        }
        for (int i = 0; i < di.size(); ++i) {
            matrix.get(i).set(i, di.get(i));
        }
        for (int i = 0; i < di.size(); ++i) {
            int ind = ia.get(i + 1) - 1;
            for (int j = i - 1; j >= 0; --j, ind--) {
                if (ind < ia.get(i)) {
                    break;
                }
                matrix.get(i).set(j, al.get(ind));
                matrix.get(j).set(i, au.get(ind));
            }
        }

        for (int i = 0; i < di.size(); ++i) {
            System.out.println(matrix.get(i));
        }

        System.out.println();
        System.out.println(f);

        for (int i = 0; i < f.size(); ++i) {
            double sum = f.get(i);
            for (int k = i - ia.get(i + 1) + ia.get(i), index = ia.get(i); k < i; ++k, index++) {
                sum -= f.get(k) * al.get(index);
            }
            f.set(i, sum / di.get(i));
        }

        System.out.println();
        System.out.println(f);

        for (int i = f.size() - 1; i >= 0; --i) {
            double sum = f.get(i);
            for (int k = i + 1; k < f.size(); ++k) {
                if (k - ia.get(k + 1) + ia.get(k) <= i) {
                    int index = ia.get(k) + (ia.get(k + 1) - ia.get(k) - (k - i));
                    sum -= au.get(index) * f.get(k);
                }
            }
            f.set(i, sum);
        }

        System.out.println();
        System.out.println(f);
    }

    private List<Double> readFile(String directory, String filename) {
        List<Double> res = new ArrayList<>();
        Path filePath = Path.of(directory, filename);
        try(final BufferedReader fileReader = Files.newBufferedReader(filePath)) {
            res = Arrays.stream(fileReader.readLine().split(" "))
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error while reading file " + filename);
            System.err.println(e.getMessage());
        }
        return res;
    }

    private List<Integer> readFileWithInt(String directory, String filename) {
        List<Integer> res = new ArrayList<>();
        Path filePath = Path.of(directory, filename);
        try(final BufferedReader fileReader = Files.newBufferedReader(filePath)) {
            res = Arrays.stream(fileReader.readLine().split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error while reading file " + filename);
        }
        return res;
    }
}
