package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.patterns.AbstractPattern;
import sample.patterns.BlinkerPattern;
import sample.patterns.GliderPattern;
import sample.patterns.SmallExploderPattern;

import java.util.Random;

// diff width = 16
// diff height = 39
public class Main extends Application {
    private static final double SCENE_WIDTH = 600;
    private static final double SCENE_HEIGHT = 600;
    public static Scene SCENE = null;

    public static void main(String[] args) {
        launch(args);
    }

    public int random255() {
        return new Random().nextInt(255);
    }

    public int random01() {
        return new Random().nextInt(2);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setTitle("Game Of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
        SCENE = scene;
        root.setSpacing(20);

        // the game board
        GridPane grid = new GridPane();
        grid.setPrefSize(500, 500);
        GameOfLife gameOfLife = new GameOfLife(grid, 40, 30);
        root.getChildren().add(grid);


        // toolbox
        HBox toolBox = new HBox();
        toolBox.setMinWidth(SCENE_WIDTH);
        toolBox.setPrefWidth(SCENE_WIDTH);
        toolBox.setPadding(new Insets(0,0,0,20));

        // control buttons
        StateControlButton stateControlButton = new StateControlButton(gameOfLife);
        StepControlButton stepControlButton = new StepControlButton(gameOfLife);


        // speed rate controller
        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(5);
        slider.valueProperty().addListener((observableValue, oldValue, newValue) -> gameOfLife.updateSpeed(newValue.doubleValue()));

        // pattern selector
        ChoiceBox<String> patternChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
                "Clear", "Glider", "Blinker", "Small Exploder")
        );
        patternChoiceBox.getSelectionModel().select(0);
        patternChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            AbstractPattern pattern = null;
            gameOfLife.clear();
            switch (newValue.intValue()) {
                case 1:
                    pattern = new GliderPattern();
                    break;
                case 2:
                    pattern = new BlinkerPattern();
                    break;
                case 3:
                    pattern = new SmallExploderPattern();
                    break;
            }
            if (pattern != null)
                gameOfLife.drawPattern(10, 10, pattern);

        });

        Label stepCounterLabel = new Label("0");

        // bind the step property in the game of life class with the stepCounterlabel
        gameOfLife.getStepProperty().addListener((observable, oldValue, newValue) -> stepCounterLabel.setText(newValue.intValue() + ""));


        toolBox.getChildren().addAll(stateControlButton, stepControlButton, slider, patternChoiceBox, stepCounterLabel);
        toolBox.setSpacing(20d);

        root.getChildren().add(toolBox);

    }
}
