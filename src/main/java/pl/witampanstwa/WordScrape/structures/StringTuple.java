package pl.witampanstwa.wordscrape.structures;

public class StringTuple {
    private final String left;
    private final String right;

    public StringTuple(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }
}
