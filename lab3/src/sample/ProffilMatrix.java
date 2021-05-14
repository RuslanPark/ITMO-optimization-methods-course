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

public class ProffilMatrix {
    private List<Double> di;
    private List<Double> al;
    private List<Double> au;
    private List<Integer> ia;
    private List<Integer> swaps;

    public void readMatrix(String directory) {
        di = Util.readFile(directory, "di.txt");
        al = Util.readFile(directory, "al.txt");
        au = Util.readFile(directory, "au.txt");
        ia = Util.readFile(directory, "ia.txt").stream()
                .map(Double::intValue)
                .collect(Collectors.toList());

        swaps = new ArrayList<>();
        for (int i = 0; i < di.size(); ++i) {
            swaps.add(i);
        }
    }

    public void swap(int r1, int r2) {
        int temp = swaps.get(r1);
        swaps.set(r1, swaps.get(r2));
        swaps.set(r2, temp);
    }

    public double get(int i, int j) {
        int row = swaps.get(i);
        if (row < j) {
            if (j - row <= ia.get(j + 1) - ia.get(j)) {
                return au.get(ia.get(j) + (ia.get(j + 1) - ia.get(j) - (j - row)));
            } else {
                return 0;
            }
        } else if (row == j) {
            return di.get(row);
        } else {
            if (row - j <= ia.get(row + 1) - ia.get(row)) {
                return al.get(ia.get(row) + (ia.get(row + 1) - ia.get(row) - (row - j)));
            } else {
                return 0;
            }
        }
    }

    public void set(int i, int j, double value) {
        int row = swaps.get(i);
        if (row < j) {
            if (j - row <= ia.get(j + 1) - ia.get(j)) {
                au.set(ia.get(j) + (ia.get(j + 1) - ia.get(j) - (j - row)), value);
            }
        } else if (row == j) {
            di.set(row, value);
        } else {
            if (row - j <= ia.get(row + 1) - ia.get(row)) {
                al.set(ia.get(row) + (ia.get(row + 1) - ia.get(row) - (row - j)), value);
            }
        }
    }

    public int size() {
        return di.size();
    }

    public void printMatrix() {
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
    }
}
