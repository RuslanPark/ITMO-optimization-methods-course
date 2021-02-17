package sample;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class Dichotomy {

    int left, right;

    Dichotomy() {
        left = 0;
        right = 100;
    }

    public boolean check(int middle) {
        return middle < 51;
    }

    public List< Pair<Number, Number> > calculate() {

        List< Pair<Number, Number> > list = new ArrayList< Pair<Number, Number> >();

        list.add(new Pair<>(left, right));
        while (right - left > 1) {
            int middle = (left + right) / 2;
            if (check(middle)) {
                left = middle;
            } else {
                right = middle;
            }
            list.add(new Pair<>(left, right));
        }

        return list;
    }
}
