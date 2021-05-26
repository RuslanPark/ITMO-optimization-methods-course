package methods;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileMatrix implements Matrix {
    private final List<Double> di;//Array with diagonal elements
    private final List<Double> al;//Bottom triangle
    private final List<Double> au;//Top triangle
    private final List<Integer> ia;//Profile
    private final List<Integer> swaps;//Array with row swaps

    //Constructor build profile matrix from matrix
    public ProfileMatrix(List<List<Double>> matrix) {
        di = new ArrayList<>();
        for (int i = 0; i < matrix.size(); ++i) {
            di.add(matrix.get(i).get(i));
        }

        al = new ArrayList<>();
        au = new ArrayList<>();
        ia = new ArrayList<>();
        for (int i = 0; i < matrix.size(); ++i) {
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

        swaps = new ArrayList<>();
        for (int i = 0; i < di.size(); ++i) {
            swaps.add(i);
        }
    }

    //Read profile matrix from files in directory
    public ProfileMatrix(String directory) {
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

    //Swap 2 rows
    public void swap(int r1, int r2) {
        int temp = swaps.get(r1);
        swaps.set(r1, swaps.get(r2));
        swaps.set(r2, temp);
    }

    //Get element with indexes i,j
    @Override
    public double get(int i, int j) {
        int row = swaps.get(i);//Get row number in saved matrix
        if (row < j) {//If element in bottom triangle
            if (j - row <= ia.get(j + 1) - ia.get(j)) {//If element consist
                return au.get(ia.get(j) + (ia.get(j + 1) - ia.get(j) - (j - row)));
            } else {
                return 0;
            }
        } else if (row == j) {//If element on diagonal
            return di.get(row);
        } else {//If element in upper triangle
            if (row - j <= ia.get(row + 1) - ia.get(row)) {// If element consist
                return al.get(ia.get(row) + (ia.get(row + 1) - ia.get(row) - (row - j)));
            } else {
                return 0;
            }
        }
    }

    //Set value on position with indexes i,j
    @Override
    public void set(int i, int j, double value) {
        int row = swaps.get(i);//Get row number in saved matrix
        if (row < j) {//If element in bottom triangle
            if (j - row <= ia.get(j + 1) - ia.get(j)) {//If element consist
                au.set(ia.get(j) + (ia.get(j + 1) - ia.get(j) - (j - row)), value);
            }
        } else if (row == j) {//If element on diagonal
            di.set(row, value);
        } else {//If element in upper triangle
            if (row - j <= ia.get(row + 1) - ia.get(row)) {//If element consist
                al.set(ia.get(row) + (ia.get(row + 1) - ia.get(row) - (row - j)), value);
            }
        }
    }

    //Returns matrix size
    @Override
    public int size() {
        return di.size();
    }

    //Write matrix in files in directory
    @Override
    public void writeMatrix(String directory) {
        Path path = Path.of(directory);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            System.err.println("Can't create directory");
            return;
        }
        Util.writeFile(directory, "di.txt", di);
        Util.writeFile(directory, "al.txt", al);
        Util.writeFile(directory, "au.txt", au);
        Util.writeFile(directory, "ia.txt", ia);
    }
}
