package pl.witampanstwa.wordscrape.structures;

import java.util.List;

public class Match {
    private final List<String> values;
    private final List<IntTuple> rangeBoundaries;
    private final boolean isWeak;
    private final boolean wasInDoubt;

    public Match(List<String> value, List<IntTuple> rangeBoundaries, boolean isWeak, boolean wasInDoubt) {
        this.values = value;
        this.rangeBoundaries = rangeBoundaries;
        this.isWeak = isWeak;
        this.wasInDoubt = wasInDoubt;
    }

    public List<String> getValues() {
        return values;
    }

    public List<IntTuple> getRangeBoundaries() {
        return rangeBoundaries;
    }

    public boolean isWeak() {
        return isWeak;
    }

    public boolean isWasInDoubt() {
        return wasInDoubt;
    }
}
