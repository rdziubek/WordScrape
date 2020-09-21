package pl.witampanstwa.wordscrape.structures;

public class IntTuple {
    private final int key;
    private final int value;

    public IntTuple(int key, int value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}
