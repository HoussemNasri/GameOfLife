package sample.patterns;

public class GliderPattern extends AbstractPattern {

    public GliderPattern() {
        rowCount = 5;
        columnCount = 5;
        pattern = new boolean[][]{
                {false, false, false, false, false},
                {false, false, true, false, false},
                {false, false, false, true, false},
                {false, true, true, true, false},
                {false, false, false, false, false}};

    }
}
