package pl.witampanstwa.wordscrape.structures;


public class RowIntersection {
    private final int sourceIndex;
    private final int targetIndex;
    private final Building sourceModel;
    private final Building targetModel;
    private final boolean isWeak;
    private final boolean wasInDoubt;

    /**
     * @param sourceIndex
     * @param targetIndex
     * @param sourceModel
     * @param targetModel
     * @param isWeak      describes whether the street name match relied on the string-hamming /
     *                    Levenshtein distance with a non-zero result.
     * @param wasInDoubt  describes whether the record contained more than one street name, and/or the targetModel
     *                    expanded numbers contained more than one occurrence of sourceModel's expanded numbers.
     */
    public RowIntersection(int sourceIndex, int targetIndex,
                           Building sourceModel, Building targetModel,
                           boolean isWeak, boolean wasInDoubt) {
        this.sourceIndex = sourceIndex;
        this.targetIndex = targetIndex;
        this.sourceModel = sourceModel;
        this.targetModel = targetModel;
        this.isWeak = isWeak;
        this.wasInDoubt = wasInDoubt;
    }

    public int getSourceIndex() {
        return sourceIndex;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    public Building getSourceModel() {
        return sourceModel;
    }

    public Building getTargetModel() {
        return targetModel;
    }

    public boolean isWeak() {
        return isWeak;
    }

    public boolean wasInDoubt() {
        return wasInDoubt;
    }
}
