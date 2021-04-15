package view;

import javafx.application.Application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;

import javafx.scene.Camera;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Home;

public class Main extends Application {

    public static double WIDTH;
    public static double HEIGHT;

    double anchorX, anchorY;
    double anchorAngleX = 0;
    double anchorAngleY = 0;

    final DoubleProperty angleX = new SimpleDoubleProperty(0);
    final DoubleProperty angleY = new SimpleDoubleProperty(0);

    Home home;
    Scene scene;
    Camera camera;

    Rotate xRotate;
    Rotate yRotate;

    @Override
    public void start(Stage window) throws Exception {

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        HEIGHT = primaryScreenBounds.getHeight();
        WIDTH = primaryScreenBounds.getWidth();

        home = new Home();
        camera = home.camera;
        scene = new Scene(home, WIDTH, HEIGHT, true);

        initMouseControl();

        window.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> {
            double delta = scrollEvent.getDeltaY();
            camera.translateZProperty().set(camera.getTranslateZ() + delta);
        });
        window.setTitle("Graph Plotter");
        window.setScene(scene);
        //window.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        window.show();
    }

    private void initMouseControl() {

        scene.setOnMousePressed(mouseEvent -> {

            xRotate = new Rotate(0, Rotate.X_AXIS);
            yRotate = new Rotate(0, Rotate.Y_AXIS);

            Home.graph3DSpace.getTransforms().addAll(xRotate, yRotate);

            xRotate.angleProperty().bind(angleX);
            yRotate.angleProperty().bind(angleY);

            anchorX = mouseEvent.getSceneX();
            anchorY = mouseEvent.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
        });

        scene.setOnMouseDragged(mouseDragEvent -> {
            angleX.set(anchorAngleX - (anchorY - mouseDragEvent.getSceneY()));
            angleY.set(anchorAngleY + (anchorX - mouseDragEvent.getSceneX()));
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
