package model;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class LeftMenu extends Group {
    Graph3DSpace graph3DSpace;
    Button[] function = new Button[3];
    VBox vBox;

    Slider xSlider;
    Slider ySlider;
    Slider zSlider;

    Label rotateBy;
    Label colorLabel;
    Label xLabel;
    Label yLabel;
    Label zLabel;

    MenuBar menuBar;
    Menu control;

    boolean axis = true;

    ColorPicker colorPicker;

    LeftMenu(Graph3DSpace graph3DSpace) {
        this.graph3DSpace = graph3DSpace;
        vBox = new VBox();
        vBox.setSpacing(3);
        menuBar = new MenuBar();
        control = new Menu("Control");

        MenuItem clearItem = new MenuItem("Clear");
        control.getItems().add(clearItem);
        MenuItem showAxisItem = new MenuItem("Show/Hide Axis");
        control.getItems().add(showAxisItem);

        menuBar.getMenus().addAll(control);
        menuBar.setBackground(new Background(new BackgroundFill(Color.SILVER, null, null)));
        vBox.getChildren().add(menuBar);

        clearItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Home.clear();
                clearButtons();
            }
        });

        showAxisItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (axis) {
                    axis = false;
                    Home.disableAxis();
                } else {
                    axis = true;
                    Home.buildAxis();
                }
            }
        });

        for (int i = 0; i < function.length; i++) {
            function[i] = new Button("F(x, y) = " + EvaluationFunction.function[i]);
            function[i].setBackground(new Background(new BackgroundFill(Color.rgb(223, 223, 223), null, null)));
            function[i].setPrefSize(200, 90);
            String id = "" + i;
            function[i].setId(id);
            function[i].setVisible(true);
            final int index = i;
            function[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    clearButtons();
                    Home.addGraph(index, colorPicker.getValue());
                }
            });
            vBox.getChildren().add(function[i]);
        }
        function[0].setVisible(true);

        colorLabel = new Label("Chose graphs colour");
        colorLabel.setScaleX(1);
        colorLabel.setScaleY(1);
        colorLabel.setTextFill(Color.BROWN);
        vBox.getChildren().add(colorLabel);
        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.rgb(153, 77, 102));
        vBox.getChildren().add(colorPicker);

        rotateBy = new Label("Rotate By Axis");
        rotateBy.setScaleX(1);
        rotateBy.setScaleY(1);
        rotateBy.setTextFill(Color.BROWN);
        vBox.getChildren().add(rotateBy);

        xSlider = new Slider();
        ySlider = new Slider();
        zSlider = new Slider();
        xLabel = new Label("X Axis");
        yLabel = new Label("Y Axis");
        zLabel = new Label("Z Axis");
        xLabel.setTextFill(Color.RED);
        yLabel.setTextFill(Color.GREEN);
        zLabel.setTextFill(Color.BLUE);

        xSlider.setMax(360);
        xSlider.setValue(0);
        xSlider.setShowTickLabels(true);
        xSlider.setShowTickMarks(true);
        xSlider.setMajorTickUnit(90);
        xSlider.setMinorTickCount(2);
        xSlider.setBlockIncrement(10);

        ySlider.setMax(360);
        ySlider.setValue(0);
        ySlider.setShowTickLabels(true);
        ySlider.setShowTickMarks(true);
        ySlider.setMajorTickUnit(90);
        ySlider.setMinorTickCount(2);
        ySlider.setBlockIncrement(10);

        zSlider.setMax(360);
        zSlider.setValue(0);
        zSlider.setShowTickLabels(true);
        zSlider.setShowTickMarks(true);
        zSlider.setMajorTickUnit(90);
        zSlider.setMinorTickCount(2);
        zSlider.setBlockIncrement(10);

        xSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                int delta = oldNumber.intValue() - newNumber.intValue();
                graph3DSpace.rotateByX(delta);
            }
        });
        ySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                int delta = oldNumber.intValue() - newNumber.intValue();
                graph3DSpace.rotateByY(delta);
            }
        });
        zSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                int delta = oldNumber.intValue() - newNumber.intValue();
                graph3DSpace.rotateByZ(delta);
            }
        });
        vBox.getChildren().addAll(xLabel, xSlider, yLabel, ySlider, zLabel, zSlider);
        this.getChildren().add(vBox);
    }

    void clearButtons() {
        for (int i = 0; i < function.length; i++) {
            function[i].setText("F(x, y) = " + EvaluationFunction.function[i]);
            function[i].setBackground(new Background(new BackgroundFill(Color.rgb(223, 223, 223), null, null)));
        }
    }

}
