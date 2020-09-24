package pl.witampanstwa.wordscrape.structures;

public class Range {
    private final IntTuple boundaryIndices;
    private final StringTuple boundaryValues;

    public Range(IntTuple boundaryIndices) {
        this.boundaryIndices = boundaryIndices;
        this.boundaryValues = null;

    }

    public Range(StringTuple boundaryValues) {
        this.boundaryIndices = null;
        this.boundaryValues = boundaryValues;
    }

    public IntTuple getBoundaryIndices() {
        return boundaryIndices;
    }

    public StringTuple getBoundaryValues() {
        return boundaryValues;
    }
}
