package pl.witampanstwa.wordscrape.structures;

public class IntTuple {
    private final int left;
    private final int right;

    public IntTuple(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
}
