package sample.patterns;

public class BlinkerPattern extends AbstractPattern {

    public BlinkerPattern() {
        rowCount = 5;
        columnCount = 5;
        pattern = new boolean[][]{
                {false, false, false, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, false, false, false}};

    }
}
