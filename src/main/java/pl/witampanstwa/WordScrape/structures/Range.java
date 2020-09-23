package pl.witampanstwa.wordscrape.structures;

public class Range {
    private final IntTuple boundary;

    public Range(IntTuple boundary) {
        this.boundary = boundary;
    }

    public IntTuple getBoundary() {
        return boundary;
    }
}
