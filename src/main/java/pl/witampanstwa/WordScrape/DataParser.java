package pl.witampanstwa.wordscrape;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Processes collected objects / collections of objects.
 * If street's key equals the number's key, these belong to the same record.
 */
public class DataParser {
    final List<RowIntersection> intersectedRows = new ArrayList<>();
    final List<Pair<Integer, Integer>> streetPairs;
    final List<Pair<Integer, Integer>> numberPairs;

    public DataParser(List<Building> itemsLookedFor, List<Building> itemsLookedThrough) {
        List<List<String>> streetsLookedFor = itemsLookedFor.stream()
                .map(Building::getStreet)
                .collect(Collectors.toList());
        List<List<String>> streetsLookedThrough = itemsLookedThrough.stream()
                .map(Building::getStreet)
                .collect(Collectors.toList());
        List<List<String>> numbersLookedFor = itemsLookedFor.stream()
                .map(Building::getNumbers)
                .collect(Collectors.toList());
        List<List<String>> numbersLookedThrough = itemsLookedThrough.stream()
                .map(Building::getNumbers)
                .collect(Collectors.toList());
        streetPairs = getStreetIntersections(streetsLookedFor, streetsLookedThrough);
        numberPairs = getNumberIntersections(numbersLookedFor, numbersLookedThrough);

        for (Pair<Integer, Integer> streetsPair : streetPairs) {
            for (Pair<Integer, Integer> numbersPair : numberPairs) {
                if (streetsPair.getKey().equals(numbersPair.getKey())
                        && streetsPair.getValue().equals(numbersPair.getValue())) {

                    // Street and number pairs are tied together at this point
                    int sourceIntersectionIndex = streetsPair.getKey();
                    int targetIntersectionIndex = streetsPair.getValue();

                    intersectedRows.add(
                            new RowIntersection(sourceIntersectionIndex, targetIntersectionIndex,
                                    itemsLookedFor.get(sourceIntersectionIndex),
                                    itemsLookedThrough.get(targetIntersectionIndex)));
                }
            }
        }
    }

    public List<RowIntersection> getIntersectedRows() {
        return intersectedRows;
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
    private List<Pair<Integer, Integer>> getStreetIntersections(List<List<String>> streetsLookedFor,
                                                                List<List<String>> streetsLookedThrough) {
        List<Pair<Integer, Integer>> matchingTuples = new ArrayList<>();
        for (int sourceIterator = 0; sourceIterator < streetsLookedFor.size(); sourceIterator++) {
            for (String unarySourceStreet : streetsLookedFor.get(sourceIterator)) {
                for (int targetIterator = 0; targetIterator < streetsLookedThrough.size(); targetIterator++) {
                    for (String unaryTargetStreet : streetsLookedThrough.get(targetIterator)) {
                        if (unarySourceStreet.equals(unaryTargetStreet)) {
                            matchingTuples.add(new Pair<>(sourceIterator, targetIterator));
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
    private List<Pair<Integer, Integer>> getNumberIntersections(List<List<String>> numbersLookedFor,
                                                                List<List<String>> numbersLookedThrough) {
        List<Pair<Integer, Integer>> matchingTuples = new ArrayList<>();
        for (int sourceIterator = 0; sourceIterator < numbersLookedFor.size(); sourceIterator++) {
            for (String unarySourceNumber : numbersLookedFor.get(sourceIterator)) {
                for (int targetIterator = 0; targetIterator < numbersLookedThrough.size(); targetIterator++) {
                    NumbersProcessor numbersProcessor = new NumbersProcessor(numbersLookedThrough.get(targetIterator));
                    for (String unaryTargetNumber : numbersProcessor.getProcessed()) {
                        if (unarySourceNumber.equals(unaryTargetNumber)) {
                            matchingTuples.add(new Pair<>(sourceIterator, targetIterator));
                        }
                    }
                }
            }
        }

        return matchingTuples;
    }
}
