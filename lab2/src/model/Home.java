package model;

import javafx.scene.Camera;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import view.Main;


public class Home extends GridPane {

    SubScene graphScene;
    SubScene menuScene;

    static double AXIS_LEN = 200;
    static double AXIS_RADIUS = 0.5;

    static public Graph3DSpace graph3DSpace;
    static LeftMenu leftMenu;

    public Camera camera;

    static Cylinder xAxis = new Cylinder(AXIS_RADIUS, AXIS_LEN + 30);
    static Cylinder yAxis = new Cylinder(AXIS_RADIUS, AXIS_LEN + 30);
    static Cylinder zAxis = new Cylinder(AXIS_RADIUS, AXIS_LEN + 30);
    static Sphere xHead = new Sphere(3);
    static Sphere yHead = new Sphere(3);
    static Sphere zHead = new Sphere(3);

    public Home() {
        graph3DSpace = new Graph3DSpace();
        leftMenu = new LeftMenu(graph3DSpace);

        xAxis.setMaterial(new PhongMaterial(Color.RED));
        yAxis.setMaterial(new PhongMaterial(Color.GREEN));
        zAxis.setMaterial(new PhongMaterial(Color.BLUE));

        xAxis.getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
        zAxis.getTransforms().add(new Rotate(90, Rotate.X_AXIS));

        xHead.translateXProperty().set(xHead.getTranslateX() + (AXIS_LEN / 2) + 15);
        yHead.translateYProperty().set(yHead.getTranslateY() + (AXIS_LEN / 2) + 15);
        zHead.translateZProperty().set(zHead.getTranslateZ() + (AXIS_LEN / 2) + 15);

        xHead.setMaterial(new PhongMaterial(Color.RED));
        yHead.setMaterial(new PhongMaterial(Color.GREEN));
        zHead.setMaterial(new PhongMaterial(Color.BLUE));
        plotGrid();
        buildAxis();

        menuScene = new SubScene(leftMenu, 200, Main.HEIGHT);
        graphScene = new SubScene(graph3DSpace, Main.WIDTH - 200, Main.HEIGHT, true, SceneAntialiasing.BALANCED);

        camera = new PerspectiveCamera(true);
        graphScene.setCamera(camera);

        camera.setNearClip(1);
        camera.setFarClip(3000);

        camera.translateXProperty().set(0);
        camera.translateYProperty().set(0);
        camera.translateZProperty().set(-600);

        this.add(menuScene, 0, 0);
        this.add(graphScene, 1, 0);
    }

    void plotGrid() {
        Cylinder[] xParallel = new Cylinder[10];
        Cylinder[] yParallel = new Cylinder[10];

        for (int i = 0; i < 4; i++) {
            xParallel[i] = new Cylinder(0.15, AXIS_LEN + 30);
            xParallel[i].getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
            xParallel[i].translateYProperty().set(xParallel[i].getTranslateY() - ((i + 1) * (AXIS_LEN / 8)));
            xParallel[i].setMaterial(new PhongMaterial(Color.BLACK));

            yParallel[i] = new Cylinder(0.15, AXIS_LEN + 30);
            yParallel[i].translateXProperty().set(yParallel[i].getTranslateX() - ((i + 1) * (AXIS_LEN / 8)));
            yParallel[i].setMaterial(new PhongMaterial(Color.BLACK));

            graph3DSpace.getChildren().addAll(xParallel[i], yParallel[i]);
        }

        for (int i = 4; i < 10; i++) {
            xParallel[i] = new Cylinder(0.15, AXIS_LEN + 30);
            xParallel[i].getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
            xParallel[i].translateYProperty().set(xParallel[i].getTranslateY() + ((i - 5) * (AXIS_LEN / 8)));
            xParallel[i].setMaterial(new PhongMaterial(Color.BLACK));

            yParallel[i] = new Cylinder(0.15, AXIS_LEN + 30);
            yParallel[i].translateXProperty().set(yParallel[i].getTranslateX() + ((i - 5) * (AXIS_LEN / 8)));
            yParallel[i].setMaterial(new PhongMaterial(Color.BLACK));

            graph3DSpace.getChildren().addAll(xParallel[i], yParallel[i]);
        }
    }

    static public void buildAxis() {
        graph3DSpace.getChildren().addAll(xAxis, yAxis, zAxis, xHead, yHead, zHead);
    }

    static void disableAxis() {
        graph3DSpace.getChildren().removeAll(xAxis, yAxis, zAxis, xHead, yHead, zHead);
    }

    static void addGraph(int functionNum, Color color) {
        BuildFunctionGraph explicitFunction = new BuildFunctionGraph(functionNum);
        explicitFunction.setColor(color);
        explicitFunction.evaluate();
    }

    static void clear() {
        graph3DSpace.clear();
    }
}
