package pl.witampanstwa.wordscrape;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Processes collected objects / collections of objects
 */
public class DataParser {
    private int whatIndexMatchedAt;
    private int whereIndexMatchedAt;

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
    }

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

    public int getWhatIndexMatchedAt() {
        return whatIndexMatchedAt;
    }

    public int getWhereIndexMatchedAt() {
        return whereIndexMatchedAt;
    }
}
