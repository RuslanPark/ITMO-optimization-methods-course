package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LUDecomposition {
    public void calculate(String directory) {
        List<Double> di = readFile(directory, "di");
        List<Double> al = readFile(directory, "di");
        List<Double> au = readFile(directory, "di");
        List<Integer> ia = readFileWithInt(directory, "di");
        List<Double> f = readFile(directory, "di");

        for (int i = 1; i < di.size(); ++i) {
            for (int j = i - ia.get(i + 1) + ia.get(i); j < i; ++j) {
                double sum = 0;
                int lk = ia.get(i + 1) - ia.get(i);
                int uk = ia.get(j + 1) - ia.get(j);
                int lInd = ia.get(i);
                int uInd = ia.get(j);
                for (int k = i - Math.min(lk, uk); k < j; ++k, lInd++, uInd++) {
                    sum += al.get(lInd) * au.get(uInd);
                }
                
            }
        }
    }

    private List<Double> readFile(String directory, String filename) {
        List<Double> res = new ArrayList<>();
        Path filePath = Path.of(directory, filename);
        try(final BufferedReader fileReader = Files.newBufferedReader(filePath)) {
            res = Arrays.stream(fileReader.readLine().split(" "))
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error while writing file " + filename);
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
            System.err.println("Error while writing file " + filename);
        }
        return res;
    }
}
