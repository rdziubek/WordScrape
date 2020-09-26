package pl.witampanstwa.wordscrape.structures;

public class Boundary {
    private static final String BOUNDARY_DIFFERENTIATOR = "~~";

    private final IntTuple indices;
    private final StringTuple values;

    public Boundary(IntTuple indices) {
        this.indices = indices;
        this.values = null;
    }

    public Boundary(StringTuple values) {
        this.indices = null;
        this.values = values;
    }

    public static String makeDifferentiator(String string) {
        return BOUNDARY_DIFFERENTIATOR + string + BOUNDARY_DIFFERENTIATOR;
    }

    public static String stripDifferentiator(String string) {
        return string.replaceAll(BOUNDARY_DIFFERENTIATOR, "");
    }

    public IntTuple getIndices() {
        return indices;
    }

    public StringTuple getValues() {
        return values;
    }
}
