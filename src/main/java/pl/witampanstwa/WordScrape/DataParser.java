package pl.witampanstwa.wordscrape;

import pl.witampanstwa.wordscrape.structures.Building;
import pl.witampanstwa.wordscrape.structures.IntTuple;
import pl.witampanstwa.wordscrape.structures.RowIntersection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Processes collected objects / collections of objects.
 * If street's key equals the number's key, these belong to the same record.
 */
public class DataParser {
    final List<RowIntersection> intersections = new ArrayList<>();
    final List<IntTuple> intersectedStreets;
    final List<IntTuple> intersectedNumbers;

    public DataParser(List<Building> itemsLookedFor, List<Building> itemsLookedThrough) {
        List<List<String>> streetsLookedFor = itemsLookedFor.stream()
                .map(Building::getStreets)
                .collect(Collectors.toList());
        List<List<String>> streetsLookedThrough = itemsLookedThrough.stream()
                .map(Building::getStreets)
                .collect(Collectors.toList());
        List<List<String>> numbersLookedFor = itemsLookedFor.stream()
                .map(Building::getNumbers)
                .collect(Collectors.toList());
        List<List<String>> numbersLookedThrough = itemsLookedThrough.stream()
                .map(Building::getNumbers)
                .collect(Collectors.toList());
        intersectedStreets = getStreetIntersections(streetsLookedFor, streetsLookedThrough);
        intersectedNumbers = getNumberIntersections(numbersLookedFor, numbersLookedThrough);

        for (IntTuple streets : intersectedStreets) {
            for (IntTuple numbers : intersectedNumbers) {
                if (streets.getKey() == numbers.getKey()
                        && streets.getValue() == numbers.getValue()) {

                    // Street and number pairs are tied together at this point
                    int sourceIntersectionIndex = streets.getKey();
                    int targetIntersectionIndex = streets.getValue();

                    intersections.add(
                            new RowIntersection(sourceIntersectionIndex, targetIntersectionIndex,
                                    itemsLookedFor.get(sourceIntersectionIndex),
                                    itemsLookedThrough.get(targetIntersectionIndex)));
                }
            }
        }
    }

    public List<RowIntersection> getIntersections() {
        return intersections;
    }

    /**
     * Can return less/more than record count as there can be zero / multiple matches in the target row array--
     * don't rely on the row count since this point.
     *
     * @param streetsLookedFor     streets part of the source input
     * @param streetsLookedThrough streets part of the target input
     * @return scattered source-target number-part-only relations, independent from the street part and therefore
     * not tied to a row as a whole
     */
    private List<IntTuple> getStreetIntersections(List<List<String>> streetsLookedFor,
                                                  List<List<String>> streetsLookedThrough) {
        List<IntTuple> matchingTuples = new ArrayList<>();
        for (int sourceIterator = 0; sourceIterator < streetsLookedFor.size(); sourceIterator++) {
            for (String unarySourceStreet : streetsLookedFor.get(sourceIterator)) {
                for (int targetIterator = 0; targetIterator < streetsLookedThrough.size(); targetIterator++) {
                    for (String unaryTargetStreet : streetsLookedThrough.get(targetIterator)) {
                        if (unarySourceStreet.equals(unaryTargetStreet)) {
                            matchingTuples.add(new IntTuple(sourceIterator, targetIterator));
                        }
                    }
                }
            }
        }

        return matchingTuples;
    }

    /**
     * Acts similar to getStreetIntersections.
     *
     * @param numbersLookedFor     Acts similar to getStreetIntersections.
     * @param numbersLookedThrough Requires further processing, as contrary to getStreetIntersections.
     * @return Acts similar to getStreetIntersections.
     */
    private List<IntTuple> getNumberIntersections(List<List<String>> numbersLookedFor,
                                                  List<List<String>> numbersLookedThrough) {
        List<IntTuple> matchingTuples = new ArrayList<>();
        for (int sourceIterator = 0; sourceIterator < numbersLookedFor.size(); sourceIterator++) {
            for (String unarySourceNumber : numbersLookedFor.get(sourceIterator)) {
                for (int targetIterator = 0; targetIterator < numbersLookedThrough.size(); targetIterator++) {
                    NumbersProcessor numbersProcessor = new NumbersProcessor(numbersLookedThrough.get(targetIterator));
                    for (int rangeIterator = 0; rangeIterator < numbersProcessor.getProcessed().size(); rangeIterator++) {
                        for (String unaryTargetNumber : numbersProcessor.getProcessed().get(rangeIterator)) {
                            if (unarySourceNumber.equals(unaryTargetNumber)) {
                                matchingTuples.add(new IntTuple(sourceIterator, targetIterator));
                            }
                        }
                    }
                }
            }
        }

        return matchingTuples;
    }
}
