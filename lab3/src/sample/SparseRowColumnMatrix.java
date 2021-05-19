package sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SparseRowColumnMatrix implements Matrix {
    private final List<Double> di;
    private final List<Double> a;
    private final List<Integer> ja;
    private final List<Integer> ia;

    public SparseRowColumnMatrix(List<List<Double>> matrix) {
        di = new ArrayList<>();

        for (int i = 0; i < matrix.size(); ++i) {
            di.add(matrix.get(i).get(i));
        }

        a = new ArrayList<>();
        ja = new ArrayList<>();
        ia = new ArrayList<>();

        for (int i = 0; i < matrix.size(); ++i) {
            ia.add(a.size());

            for (int j = 0; j < i; ++j) {
                if (matrix.get(i).get(j) != 0) {
                    a.add(matrix.get(i).get(j));
                    ja.add(j);
                }
            }
        }

        ia.add(a.size());
    }

    @Override
    public double get(int i, int j) {
        if (i < j) {
            Integer t = i;
            i = j;
            j = t;
        }

        if (i > j) {
            int index = Arrays.binarySearch(ja.subList(ia.get(i), ia.get(i + 1)).toArray(), j);

            if (index < 0) {
                return 0;
            } else {
                return a.get(ia.get(i) + index);
            }
        } else {
            return di.get(i);
        }
    }

    @Override
    public void set(int i, int j, double value) {
        if (i > j) {
            Integer t = i;
            i = j;
            j = t;
        }

        if (i < j) {
            int index = Arrays.binarySearch(ja.subList(ia.get(i), ia.get(i + 1) - ia.get(i)).toArray(), j);

            if (index >= 0) {
                a.set(ia.get(i) + index, value);
            }
        } else {
            di.set(i, value);
        }
    }

    @Override
    public int size() {
        return di.size();
    }

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
        Util.writeFile(directory, "al.txt", a);
        Util.writeFile(directory, "ja.txt", ja);
        Util.writeFile(directory, "ia.txt", ia);
    }
}
