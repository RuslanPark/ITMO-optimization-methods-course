package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GaussMethod {
    public void calculate(String directory) {
        List<Double> di = readFile(directory, "di.txt");
        List<Double> al = readFile(directory, "al.txt");
        List<Double> au = readFile(directory, "au.txt");
        List<Integer> ia = readFileWithInt(directory, "ia.txt");
        List<Double> f = readFile(directory, "f.txt");

        List<Integer> swaps = new ArrayList<>();
        for (int i = 0; i < di.size(); ++i) {
            swaps.add(i);
        }
        for (int k = 0; k < di.size(); ++k) {
            double max;
            if (swaps.get(k) == k) {
                max = di.get(k);
            } else {
                int row = swaps.get(k);
                if (row - ia.get(row) + ia.get(row + 1) <= k) {
                    max = ia.get(row) + (row - ia.get(row) + ia.get(row + 1) - k);
                } else {
                    max = 0;
                }
            }
            for (int i = k + 1; k < di.size(); ++k) {
                if (swaps.get(i) == i) {
                    if (di.get(i) > max) {
                        max = di.get(i);
                        int temp = swaps.get(i);
                    }
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
