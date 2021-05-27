package methods;

import java.util.ArrayList;
import java.util.List;

public class ProfileMatrix implements Matrix {
    private final List<Double> di;
    private final List<Double> al;
    private final List<Double> au;
    private final List<Integer> ia;
    private final List<Integer> swaps;

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

    public void swap(int r1, int r2) {
        int temp = swaps.get(r1);
        swaps.set(r1, swaps.get(r2));
        swaps.set(r2, temp);
    }

    @Override
    public Double get(Integer i, Integer j) {
        int row = swaps.get(i);
        if (row < j) {
            if (j - row <= ia.get(j + 1) - ia.get(j)) {
                return au.get(ia.get(j) + (ia.get(j + 1) - ia.get(j) - (j - row)));
            } else {
                return 0.0;
            }
        } else if (row == j) {
            return di.get(row);
        } else {
            if (row - j <= ia.get(row + 1) - ia.get(row)) {
                return al.get(ia.get(row) + (ia.get(row + 1) - ia.get(row) - (row - j)));
            } else {
                return 0.0;
            }
        }
    }

    @Override
    public void set(Integer i, Integer j, Double value) {
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

    @Override
    public Integer size() {
        return di.size();
    }

    @Override
    public Matrix add(Matrix m1) {
        List<List<Double>> m = new ArrayList<>();

        Integer n = m1.size();

        for (int i = 0; i < n; i++) {
            m.add(new ArrayList<>());

            for (int j = 0; j < n; j++) {
                m.get(i).add(this.get(i, j) + m1.get(i, j));
            }
        }

        return new ProfileMatrix(m);
    }

    @Override
    public Matrix sub(Matrix m1) {
        List<List<Double>> m = new ArrayList<>();

        Integer n = m1.size();

        for (int i = 0; i < n; i++) {
            m.add(new ArrayList<>());

            for (int j = 0; j < n; j++) {
                m.get(i).add(this.get(i, j) - m1.get(i, j));
            }
        }

        return new ProfileMatrix(m);
    }

    @Override
    public Matrix mul_n(Double c) {
        List<List<Double>> m = new ArrayList<>();

        Integer n = this.size();

        for (int i = 0; i < n; i++) {
            m.add(new ArrayList<>());

            for (int j = 0; j < n; j++) {
                m.get(i).add(this.get(i, j) * c);
            }
        }

        return new ProfileMatrix(m);
    }

    @Override
    public Matrix divide_n(Double c) {
        List<List<Double>> m = new ArrayList<>();

        Integer n = this.size();

        for (int i = 0; i < n; i++) {
            m.add(new ArrayList<>());

            for (int j = 0; j < n; j++) {
                m.get(i).add(this.get(i, j) / c);
            }
        }

        return new ProfileMatrix(m);
    }

    static Matrix getIdentity(Integer n) {
        List<List<Double>> m = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            m.add(new ArrayList<>());

            for (int j = 0; j < n; j++) {
                m.get(i).add(i == j ? 1.0 : 0.0);

            }
        }

        return new ProfileMatrix(m);
    }
}
