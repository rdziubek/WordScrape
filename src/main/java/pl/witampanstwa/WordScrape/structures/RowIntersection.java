package pl.witampanstwa.wordscrape.structures;


public class RowIntersection {
    private final int indexItemLookedFor;
    private final int indexItemLookedThrough;
    private final Building modelLookedFor;
    private final Building modelLookedThrough;
    private final boolean isWeak;
    private final boolean wasInDoubt;

    /**
     * @param indexItemLookedFor
     * @param indexItemLookedThrough
     * @param modelLookedFor
     * @param modelLookedThrough
     * @param isWeak      describes whether the street name match relied on the string-hamming /
     *                    Levenshtein distance with a non-zero result.
     * @param wasInDoubt  describes whether the record contained more than one street name, and/or the targetModel
     *                    expanded numbers contained more than one occurrence of sourceModel's expanded numbers.
     */
    public RowIntersection(int indexItemLookedFor, int indexItemLookedThrough,
                           Building modelLookedFor, Building modelLookedThrough,
                           boolean isWeak, boolean wasInDoubt) {
        this.indexItemLookedFor = indexItemLookedFor;
        this.indexItemLookedThrough = indexItemLookedThrough;
        this.modelLookedFor = modelLookedFor;
        this.modelLookedThrough = modelLookedThrough;
        this.isWeak = isWeak;
        this.wasInDoubt = wasInDoubt;
    }

    public int getIndexItemLookedFor() {
        return indexItemLookedFor;
    }

    public int getIndexItemLookedThrough() {
        return indexItemLookedThrough;
    }

    public Building getModelLookedFor() {
        return modelLookedFor;
    }

    public Building getModelLookedThrough() {
        return modelLookedThrough;
    }

    public boolean isWeak() {
        return isWeak;
    }

    public boolean wasInDoubt() {
        return wasInDoubt;
    }
}
