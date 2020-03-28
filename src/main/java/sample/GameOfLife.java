package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.callback.StateEvent;
import sample.callback.StepEvent;
import sample.patterns.AbstractPattern;

public class GameOfLife implements StateEvent, StepEvent {
    public static final int WINDOWS_HEIGHT_OFFSET = 39;
    public static final int TOOLBOX_HEIGHT = 80;
    public static final int WINDOWS_WIDTH_OFFSET = 16;
    Cell[][] cells;
    private SimpleIntegerProperty stepProperty = new SimpleIntegerProperty(0);
    private GridPane grid;
    private Timeline timeline;
    private long STEP_DURATION = 300;
    //Colors
    private Color emptyCellColor = Color.WHITE;
    private Color filledCellColor = Color.BLACK;
    // constraints
    private int numberOfCellsHorz;
    private int numberOfCellsVert;
    private double cellSize;
    public GameOfLife(GridPane grid, int numberOfCellsHorz, int numberOfCellsVert) {
        this.numberOfCellsHorz = numberOfCellsHorz;
        this.numberOfCellsVert = numberOfCellsVert;
        cells = new Cell[numberOfCellsVert][numberOfCellsHorz];
        this.grid = grid;
        this.cellSize = 20;

        Main.SCENE.getWindow().setWidth(numberOfCellsHorz * cellSize + WINDOWS_WIDTH_OFFSET);
        Main.SCENE.getWindow().setHeight(numberOfCellsVert * cellSize + WINDOWS_HEIGHT_OFFSET + TOOLBOX_HEIGHT);
        clear();
        timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(STEP_DURATION), event -> {
            onStep();
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);

    }

    public SimpleIntegerProperty getStepProperty() {
        return stepProperty;
    }

    public boolean in(double var, double min, double max) {
        return var >= min && var <= max;
    }

    public int countNeighbours(int i, int j) {
        int count = 0;
        for (int k = -1; k < 2; k++)
            for (int l = -1; l < 2; l++)
                if (!(k == 0 && l == 0) && in(i + k, 1, numberOfCellsVert - 1) && in(j + l, 1, numberOfCellsHorz - 1)) {
                    if (!cells[i + k][j + l].isEmpty())
                        count++;
                }


        return count;
    }

    @Override
    public void onStart() {
        timeline.play();
    }

    @Override
    public void onStop() {
        timeline.stop();
    }

    public void updateSpeed(double speedRate) {
        timeline.setRate(speedRate);
    }

    public void clear() {
        grid.getChildren().clear();
        stepProperty.set(0);
        for (int i = 0; i < numberOfCellsVert; i++) {
            for (int j = 0; j < numberOfCellsHorz; j++) {
                Cell cell = new Cell(cellSize, cellSize, emptyCellColor, filledCellColor);
                grid.add(cell, j, i);
                cells[i][j] = cell;
            }
        }
    }

    public void drawPattern(int startX, int startY, AbstractPattern pattern) {
        for (int i = 0; i < pattern.getRowCount(); i++) {
            for (int j = 0; j < pattern.getColumnCount(); j++) {
                if (pattern.getPattern()[i][j]) {
                    cells[startY + i][startX + j].fillCell();
                }
            }
        }
    }

    public Cell[][] cloneMatrix(Cell[][] c1) {
        // clone matrix
        Cell[][] c2 = new Cell[c1.length][numberOfCellsHorz];
        for (int i = 0; i < numberOfCellsVert; i++) {
            for (int j = 0; j < numberOfCellsHorz; j++) {
                c2[i][j] = c1[i][j].cloneCell();
            }
        }

        return c2;
    }

    @Override
    public void onStep() {
        Cell[][] tempCell = cloneMatrix(cells);
        for (int i = 0; i < numberOfCellsVert; i++) {
            for (int j = 0; j < numberOfCellsHorz; j++) {
                int count = countNeighbours(i, j);
                if (!cells[i][j].isEmpty()) {
                    if (count < 2)
                        tempCell[i][j].clearCell();
                    if (count > 3)
                        tempCell[i][j].clearCell();
                } else {
                    if (count == 3) {
                        tempCell[i][j].fillCell();
                    }
                }
            }
        }
        grid.getChildren().clear();
        cells = cloneMatrix(tempCell);
        for (int i = 0; i < numberOfCellsVert; i++) {
            for (int j = 0; j < numberOfCellsHorz; j++) {
                grid.add(cells[i][j], j, i);
                Tooltip.install(cells[i][j], new Tooltip("[" + i + ", " + j + "] [" + countNeighbours(i, j) + "]"));
            }
        }
        stepProperty.set(stepProperty.get() + 1);
    }


}
