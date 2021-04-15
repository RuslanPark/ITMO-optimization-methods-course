package model;

import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Graph {

    public TriangleMesh surface;

    MeshView graphSurface;
    Color color;
    public double totalPoints;
    int index;

    Graph() {
        totalPoints = (int) (Home.AXIS_LEN * 2);
        index = 0;
        surface = new TriangleMesh();
        surface.getTexCoords().addAll(0, 0);

        color = Color.GRAY;

    }

    void setColor(Color color) {
        this.color = color;
    }
}
