package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
    private boolean empty;
    private Color emptyColor;
    private Color filledColor;

    public Cell(double width, double height, Color emptyColor, Color filledColor, boolean empty) {
        super(width, height);
        this.empty = empty;
        this.emptyColor = emptyColor;
        this.filledColor = filledColor;
        if (empty)
            clearCell();
        else
            fillCell();
        setOnMouseClicked(e -> {
            if (this.empty) {
                fillCell();
            } else {
                clearCell();
            }
        });
    }

    public Cell(double width, double height, Color emptyColor, Color filledColor) {
        this(width, height, emptyColor, filledColor,true);
    }

    public boolean isEmpty() {
        return empty;
    }

    public void fillCell() {
        setFill(filledColor);
        this.empty = false;
    }

    public void clearCell() {
        setFill(emptyColor);
        this.empty = true;
    }

   public Cell cloneCell () {
        return new Cell(getWidth(), getHeight(), emptyColor, filledColor, empty);
   }
}
