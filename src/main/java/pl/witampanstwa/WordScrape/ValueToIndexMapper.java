package pl.witampanstwa.wordscrape;

import pl.witampanstwa.wordscrape.structures.IntTuple;
import pl.witampanstwa.wordscrape.structures.Range;
import pl.witampanstwa.wordscrape.structures.RowIntersection;

import java.util.ArrayList;
import java.util.List;

public class ValueToIndexMapper {
    private final List<Range> absoluteRangeBoundaries = new ArrayList<>();

    public ValueToIndexMapper(List<Range> targetedBoundaryValues,
                              List<RowIntersection> intersections,
                              List<String> rows) {

        int iterator = 0;
        for (RowIntersection intersection : intersections) {
            Range containerBounds = new Range(new IntTuple(
                    intersection.getModelLookedThrough()
                            .getNumberMatchRanges()
                            .get(0)
                            .getBoundaryIndices().getLeft(),
                    intersection.getModelLookedThrough()
                            .getNumberMatchRanges()
                            .get(intersection.getModelLookedThrough()
                                    .getNumberMatchRanges()
                                    .size() - 1)
                            .getBoundaryIndices().getRight()));

            int containerStartIndex = containerBounds.getBoundaryIndices().getLeft();
            int containerEndIndex = containerBounds.getBoundaryIndices().getRight();
            String rangesContainer = rows.get(intersection.getIndexItemLookedThrough())
                    .substring(containerStartIndex, containerEndIndex);

            int absoluteUnaryRangeStartIndex = containerStartIndex + rangesContainer.indexOf(
                    targetedBoundaryValues.get(iterator)
                            .getBoundaryValues().getLeft());
            int absoluteUnaryRangeEndIndex = containerStartIndex + rangesContainer.indexOf(
                    targetedBoundaryValues.get(iterator)
                            .getBoundaryValues().getRight())
                    + targetedBoundaryValues.get(iterator)
                    .getBoundaryValues().getRight().length();
            absoluteRangeBoundaries.add(new Range(new IntTuple(
                    absoluteUnaryRangeStartIndex, absoluteUnaryRangeEndIndex)));

            iterator++;
        }
    }

    public List<Range> getAbsoluteRangeBoundaries() {
        return absoluteRangeBoundaries;
    }
}
