package pl.witampanstwa.wordscrape;

import pl.witampanstwa.wordscrape.structures.IntTuple;
import pl.witampanstwa.wordscrape.structures.Boundary;
import pl.witampanstwa.wordscrape.structures.RowIntersection;

import java.util.ArrayList;
import java.util.List;

public class ValueToIndexMapper {
    private final List<Boundary> absoluteBoundaries = new ArrayList<>();

    public ValueToIndexMapper(List<Boundary> targetedBoundaryValues,
                              List<RowIntersection> intersections,
                              List<String> rows) {

        int iterator = 0;
        for (RowIntersection intersection : intersections) {
            Boundary containerBounds = new Boundary(new IntTuple(
                    intersection.getModelLookedThrough()
                            .getNumberMatchRanges()
                            .get(0)
                            .getIndices().getLeft(),
                    intersection.getModelLookedThrough()
                            .getNumberMatchRanges()
                            .get(intersection.getModelLookedThrough()
                                    .getNumberMatchRanges()
                                    .size() - 1)
                            .getIndices().getRight()));

            int containerStartIndex = containerBounds.getIndices().getLeft();
            int containerEndIndex = containerBounds.getIndices().getRight();
            String rangesContainer = rows.get(intersection.getIndexItemLookedThrough())
                    .substring(containerStartIndex, containerEndIndex);

            int absoluteUnaryRangeStartIndex = containerStartIndex + rangesContainer.indexOf(
                    Boundary.stripDifferentiator(
                            targetedBoundaryValues.get(iterator)
                                    .getValues().getLeft()));
            int absoluteUnaryRangeEndIndex = containerStartIndex + rangesContainer.indexOf(
                    Boundary.stripDifferentiator(
                            targetedBoundaryValues.get(iterator)
                                    .getValues().getRight())) + Boundary.stripDifferentiator(
                    targetedBoundaryValues.get(iterator)
                            .getValues().getRight()).length();
            absoluteBoundaries.add(new Boundary(new IntTuple(
                    absoluteUnaryRangeStartIndex, absoluteUnaryRangeEndIndex)));

            iterator++;
        }
    }

    public List<Boundary> getAbsoluteRangeBoundaries() {
        return absoluteBoundaries;
    }
}
