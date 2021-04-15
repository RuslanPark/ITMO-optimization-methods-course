package model;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

import static java.lang.Math.abs;


public class BuildFunctionGraph extends Graph {

    EvaluationFunction equationEvaluation;
    public int functionIndex;
    public float[][] z_values;

    BuildFunctionGraph(int functionNum) {
        this.functionIndex = functionNum;
        z_values = new float[(int) totalPoints + 10][(int) totalPoints + 10];
        equationEvaluation = new EvaluationFunction(functionIndex + 1);
    }

    void evaluate() {
        for (int i = 0; i <= totalPoints; i++) {
            for (int j = 0; j <= totalPoints; j++) {
                float x = (float) ((i - (totalPoints / 2)) / 2);
                x = (float) (x / 10.0);
                float y = (float) ((j - (totalPoints / 2)) / 2);
                y = (float) (y / 10.0);
                float z = 10 * equationEvaluation.evaluate(x, y);
                if (z > 200) {
                    z = 200;
                }
                if (z < -200) {
                    z = -200;
                }
                z_values[i][j] = z;
            }

        }
        plot();
        Home.leftMenu.function[functionIndex].setBackground(new Background(new BackgroundFill(color, null, null)));

    }

    void plot() {
        surface = new TriangleMesh();
        surface.getTexCoords().addAll(0, 0);

        int k = 0;
        for (int i = 0; i < totalPoints; i++) {
            for (int j = 0; j < totalPoints; j++) {
                float x1 = (float) ((i - (totalPoints / 2)) / 2);
                float y1 = (float) ((j - (totalPoints / 2)) / 2);
                float z1 = z_values[i][j];

                float x2 = (float) (x1 + 0.5);
                float y2 = y1;
                float z2 = z_values[i + 1][j];

                float x3 = x1;
                float y3 = (float) (y1 + 0.5);
                float z3 = z_values[i][j + 1];

                float x4 = (float) (x1 + 0.5);
                float y4 = (float) (y1 + 0.5);
                float z4 = z_values[i + 1][j + 1];


                if (!((abs(abs(z1) - 200) < 0.001) && (abs(abs(z2) - 200) < 0.001) && (abs(abs(z3) - 200) < 0.001))) {
                    surface.getPoints().addAll(
                            x1, y1, z1,
                            x2, y2, z2,
                            x3, y3, z3,
                            x4, y4, z4
                    );

                    surface.getFaces().addAll(
                            k, 0, k + 1, 0, k + 2, 0,
                            k + 2, 0, k + 1, 0, k, 0,
                            k + 1, 0, k + 2, 0, k + 3, 0,
                            k + 3, 0, k + 2, 0, k + 1, 0

                    );
                    k += 4;
                }
            }
        }

        graphSurface = new MeshView(surface);
        graphSurface.setDrawMode(DrawMode.FILL);
        graphSurface.setMaterial(new PhongMaterial(color));

        Home.graph3DSpace.add(graphSurface);
    }
}
