package model;


import javafx.scene.Group;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class Graph3DSpace extends Group {
    Rotate r;
    Transform t;
    MeshView[][] meshView;
    boolean[][] isDrawn;

    Graph3DSpace() {
        meshView = new MeshView[5][3];
        isDrawn = new boolean[5][3];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                isDrawn[i][j] = false;
            }

        }
        t = new Rotate();
        rotateByX(120);
        rotateByZ(40);

    }

    void add(MeshView mView) {
        int i = 0;
        int j = 0;
        if (j == 0) {
            for (int a = 0; a < 3; a++) {
                if (isDrawn[i][a]) {
                    this.getChildren().removeAll(meshView[i][a]);
                }
            }
        } else if (isDrawn[i][j]) {
            this.getChildren().removeAll(meshView[i][j]);
        }
        meshView[i][j] = mView;
        isDrawn[i][j] = true;
        this.getChildren().add(mView);
    }

    void rotateByX(int angle) {
        r = new Rotate(angle, Rotate.X_AXIS);
        t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().add(t);
    }

    void rotateByY(int angle) {
        r = new Rotate(angle, Rotate.Y_AXIS);
        t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().add(t);
    }

    void rotateByZ(int angle) {
        r = new Rotate(angle, Rotate.Z_AXIS);
        t = t.createConcatenation(r);
        this.getTransforms().clear();
        this.getTransforms().add(t);
    }

    void clear() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (isDrawn[i][j]) {
                    this.getChildren().removeAll(meshView[i][j]);
                }
            }

        }
    }
}
