package sample.patterns;

public class SmallExploderPattern extends AbstractPattern {
    public SmallExploderPattern() {
        rowCount = 4;
        columnCount = 3;
        pattern = new boolean[][]{
                {false, true, false},
                {true, true, true},
                {true, false, true},
                {false, true, false}};

    }
}
