package model;

public class EvaluationFunction {

    final static String[] function = {
            "64*x^2+126*x*y+64*y^2-10*x+30*y+13",
            "254*x^2+506*x*y+254*y^2+50*x+130*y-111",
            //"211*x^2-420*x*y+211*y^2-192*x+50*y-25"
            "x * x + y * y"
    };

    int num;

    EvaluationFunction(int num) {
        this.num = num;
    }

    float evaluate(float x, float y) {
        if (num == 1) {
            return evaluate1(x, y);
        } else {
            if (num == 2) {
                return evaluate2(x, y);
            } else {
                return evaluate3(x, y);
            }
        }
    }

    float evaluate1(float x, float y) {
        return 64 * x * x + 126 * x * y + 64 * y * y - 10 * x + 30 * y + 13;
    }

    float evaluate2(float x, float y) {
        return 254 * x * x + 506 * x * y + 254 * y * y + 50 * x + 130 * y - 111;
    }

    float evaluate3(float x, float y) {
        //return 211 * x * x - 420 * x * y + 211 * y * y - 192 * x + 50 * y - 25;
        return x * x + y * y;
    }
}