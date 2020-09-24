package pl.witampanstwa.wordscrape;

import org.apache.commons.text.similarity.LevenshteinDistance;
import pl.witampanstwa.wordscrape.structures.Building;
import pl.witampanstwa.wordscrape.structures.IntTuple;
import pl.witampanstwa.wordscrape.structures.RowIntersection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Processes collected objects / collections of objects.
 * If street's key equals the number's key, these belong to the same record.
 * Intersection properties are loaded on the fly as the base logic / object initialisation is evaluated.
 */
public class DataParser {
    private static final int STREET_INTERSECTION_TOLERANCE_PERCENT = 34;

    final List<RowIntersection> intersections = new ArrayList<>();
    final List<IntTuple> intersectedStreets;
    final List<IntTuple> intersectedNumbers;
    final List<Boolean> isIntersectionWeak = new ArrayList<>();
    final List<Boolean> wasIntersectionInDoubt = new ArrayList<>();

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

        int streetIntersectionCounter = 0;
        for (IntTuple streets : intersectedStreets) {
            int numberIntersectionCounter = 0;
            for (IntTuple numbers : intersectedNumbers) {
                if (streets.getLeft() == numbers.getLeft()
                        && streets.getRight() == numbers.getRight()) {

                    // Street and number pairs are tied together at this point
                    int sourceIntersectionIndex = streets.getLeft();
                    int targetIntersectionIndex = streets.getRight();

                    boolean isWeak = isIntersectionWeak.get(streetIntersectionCounter);
//                    boolean wasInDoubt = wasIntersectionInDoubt.get(streetIntersectionCounter)
//                            || wasIntersectionInDoubt.get(numberIntersectionCounter);
                    boolean wasInDoubt = false;

                    intersections.add(
                            new RowIntersection(sourceIntersectionIndex, targetIntersectionIndex,
                                    itemsLookedFor.get(sourceIntersectionIndex),
                                    itemsLookedThrough.get(targetIntersectionIndex),
                                    isWeak, wasInDoubt));
                }
                numberIntersectionCounter++;
            }
            streetIntersectionCounter++;
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
                        int levenshteinDistance = new LevenshteinDistance().apply(
                                unarySourceStreet.toLowerCase(), unaryTargetStreet.toLowerCase());
                        if (levenshteinDistance == 0) {
                            isIntersectionWeak.add(false);
                            matchingTuples.add(new IntTuple(sourceIterator, targetIterator));
                        } else if (levenshteinDistance > 0
                                && levenshteinDistance <= calculateToleratedCharCountForStrings(
                                unarySourceStreet,
                                unaryTargetStreet
                        )) {
                            isIntersectionWeak.add(true);
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

    /**
     * Tolerance of 0% means max accuracy, 100% returns a positive match for every string combination.
     * Uses Math.ceil at each point of rounding, so that it tends into less accuracy matching.
     * <p>
     * In relation to the strings' sum of lengths and the tolerance percentage,
     * the manipulation represent the graph given:
     * 100   0 1 1 2 2 3 3 4 4 5 5  6  6  7
     * 75    0 1 1 2 2 3 3 3 3 4 4  5  5  6
     * 66    0 1 1 2 2 2 2 3 3 4 4  4  4  5
     * 50    0 1 1 1 1 2 2 2 2 3 3  3  3  4
     * 25    0 1 1 1 1 1 1 1 1 2 2  2  2  2
     * 0     0 0 0 0 0 0 0 0 0 0 0  0  0  0
     * <p>
     * 0 1 2 3 4 5 6 7 8 9 10 11 12 13
     */
    private int calculateToleratedCharCountForStrings(String left, String right) {
        int ceilingAverage = (int) Math.ceil((double) (left.length() + right.length()) / 2);

        return (int) Math.ceil(ceilingAverage * ((double) STREET_INTERSECTION_TOLERANCE_PERCENT / 100));
    }
}
