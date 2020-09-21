package pl.witampanstwa.wordscrape.structures;

import pl.witampanstwa.wordscrape.structures.Building;

public class RowIntersection {
    private final int sourceIndex;
    private final int targetIndex;
    private final Building sourceModel;
    private final Building targetModel;
    private final boolean doubtRaised = false;

    public RowIntersection(int sourceIndex, int targetIndex, Building sourceModel, Building targetModel) {
        this.sourceIndex = sourceIndex;
        this.targetIndex = targetIndex;
        this.sourceModel = sourceModel;
        this.targetModel = targetModel;
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

    public boolean isDoubtRaised() {
        return doubtRaised;
    }
}
