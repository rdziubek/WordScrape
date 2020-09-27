package pl.witampanstwa.wordscrape.structures;


public class RowIntersection {
    private final int indexItemLookedFor;
    private final int indexItemLookedThrough;
    private final Building modelLookedFor;
    private final Building modelLookedThrough;
    private Boundary unaryIntersectedNumberRanges;
    private final boolean isWeak;

    /**
     * @param indexItemLookedFor
     * @param indexItemLookedThrough
     * @param modelLookedFor
     * @param modelLookedThrough
     * @param isWeak                 describes whether the street name match relied on the string-hamming /
     *                               Levenshtein distance with a non-zero result.
     */
    public RowIntersection(int indexItemLookedFor, int indexItemLookedThrough,
                           Building modelLookedFor, Building modelLookedThrough,
                           Boundary unaryIntersectedNumberRanges,
                           boolean isWeak) {
        this.indexItemLookedFor = indexItemLookedFor;
        this.indexItemLookedThrough = indexItemLookedThrough;
        this.modelLookedFor = modelLookedFor;
        this.modelLookedThrough = modelLookedThrough;
        this.unaryIntersectedNumberRanges = unaryIntersectedNumberRanges;
        this.isWeak = isWeak;
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

    public Boundary getUnaryIntersectedNumberRanges() {
        return unaryIntersectedNumberRanges;
    }

    public void setUnaryIntersectedNumberRanges(Boundary unaryIntersectedNumberRanges) {
        this.unaryIntersectedNumberRanges = unaryIntersectedNumberRanges;
    }

    public boolean isWeak() {
        return isWeak;
    }
}
