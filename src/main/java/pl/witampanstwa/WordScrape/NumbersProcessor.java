package pl.witampanstwa.wordscrape;

import pl.witampanstwa.wordscrape.structures.Boundary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Analyses and decomposes single entry's numbers/ranges, where 'entry' is one data row/record.
 */
public class NumbersProcessor {
    private final List<List<String>> processed;

    public NumbersProcessor(List<String> numbers) {
        this.processed = process(numbers);
    }

    public List<List<String>> getProcessed() {
        return processed;
    }

    /**
     * Differentiates simple-case exact building numbers from ranges.
     * Eliminates the possibility of omitting an exact number in a case `["2-4", "8", "14-20"]` by
     * filtering out ranges by building these on the fly and postponing for further processing
     * instead of doing such in-place.
     * Takes care of preserving value-safety by leaving out original range extrema at the start and end of each range.
     *
     * @param numbers building's number/numbers
     * @return fully expanded exact numbers as well as ranges, with ranges delimited by match differentiators
     * accordingly
     */
    private List<List<String>> process(List<String> numbers) {
        List<List<String>> exactNumbers = new ArrayList<>();
        StringBuilder unaryRange = new StringBuilder();

        for (int currentAtomIndex = 0; currentAtomIndex < numbers.size(); currentAtomIndex++) {
            List<String> exactRanges = new ArrayList<>();
            String currentAtom = numbers.get(currentAtomIndex);
            String previousAtom = (currentAtomIndex > 0 ? numbers.get(currentAtomIndex - 1) : null);

            if (currentAtom.endsWith("-") || previousAtom != null && previousAtom.endsWith("-")) {
                unaryRange.append(currentAtom);

                if (!currentAtom.endsWith("-")) {
                    exactRanges.add(Boundary.makeDifferentiator(previousAtom));
                    exactRanges.addAll(decomposeRangeIntoExacts(unaryRange.toString()));
                    exactRanges.add(Boundary.makeDifferentiator(currentAtom));
                    unaryRange = new StringBuilder();
                }
            } else {
                exactRanges.add(currentAtom);
            }
            exactNumbers.add(exactRanges);
        }

        return exactNumbers;
    }

    /**
     * Ranges are guaranteed to be comprised of at least 2 atoms.
     * Tolerates both ascending and descending ranges, intermittent or not.
     *
     * @param range
     * @return
     */
    private List<String> decomposeRangeIntoExacts(String range) {
        List<String> decomposedExactNumbers = new ArrayList<>();
        List<String> charRangePoints = Arrays.asList(range
                .replaceAll("\\d", "")
                .split("-"));
        List<Integer> integerRangePoints = Arrays.stream(range
                .replaceAll("[A-Za-z]", "")
                .split("-"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        for (int i = 1; i < integerRangePoints.size(); i++) {
            int currentPoint = integerRangePoints.get(i);
            int previousPoint = integerRangePoints.get(i - 1);

            if (currentPoint == previousPoint - 2
                    || currentPoint == previousPoint + 2) {
                if (i == 1) {
                    decomposedExactNumbers.add(Integer.toString(previousPoint));
                }
                decomposedExactNumbers.add(Integer.toString(currentPoint));
            } else {
                if (previousPoint < currentPoint) {
                    for (int j = previousPoint; j <= currentPoint; j++) {
                        decomposedExactNumbers.add(Integer.toString(j));
                    }
                } else {
                    for (int j = currentPoint; j <= previousPoint; j++) {
                        decomposedExactNumbers.add(Integer.toString(j));
                    }
                }
            }
        }

        // TODO: Refactor into a `RangeDecomposer`
        if (!charRangePoints.isEmpty()) {
            decomposedExactNumbers = applyACharMaskVerbosely(decomposedExactNumbers, charRangePoints);
        }

        return decomposedExactNumbers;
    }

    /**
     * Distributes characters evenly from extrema to the centre of the range.
     *
     * @param numericalExpandedRange
     * @param mask
     * @return
     */
    private List<String> applyACharMaskInPlace(List<String> numericalExpandedRange, List<String> mask) {
        int rangeSize = numericalExpandedRange.size();
        int charCount = mask.size();
        List<String> maskedRange = new ArrayList<>(numericalExpandedRange);

        for (int charIndex = 0; charIndex < charCount; charIndex++) {
            for (int relativeNumberIndex = rangeSize - (int) Math.ceil((double) rangeSize / (charIndex + 1));
                 relativeNumberIndex < rangeSize / charCount;
                 relativeNumberIndex++) {
                maskedRange.set(
                        relativeNumberIndex,
                        maskedRange.get(relativeNumberIndex) + mask.get(charIndex));
            }
        }

        return maskedRange;
    }

    /**
     * Distributes characters by applying every single one of these onto a whole range.
     * Passed in range-points are processed after removal of all char duplicates--it is important not to remove
     * all point duplicates from the mask itself like this and only check if a single point a duplicate of the one
     * previously checked as a range can be comprised of more than 2 points, e.g. `aa-bb-aa`--in this particular case
     * it is only desired to get rid of nested duplicates only.
     *
     * @param numericalExpandedRange has size <= `mask`'s size
     * @param mask                   can have size non-equal to the one of `numericalExpandedRange`
     * @return fully expanded range
     */
    private List<String> applyACharMaskVerbosely(List<String> numericalExpandedRange, List<String> mask) {
        List<String> maskedRange = new ArrayList<>();

        int unaryMaskingCharsIndex = 0;
        for (String maskPoint : mask) {
            String previousMaskPoint = (unaryMaskingCharsIndex > 0
                    ? mask.get(unaryMaskingCharsIndex - 1)
                    : null);

            if (previousMaskPoint == null
                    || !maskPoint.chars().mapToObj(c -> (char) c).collect(Collectors.toList()).containsAll(
                    previousMaskPoint.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))) {

                String simplifiedMaskPoint = simplifyMaskPoint(maskPoint);

                // Is single range-point mask not empty?
                if (!simplifiedMaskPoint.equals("")) {
                    for (char singleChar : simplifiedMaskPoint.toCharArray()) {
                        String maskingChar = Character.toString(singleChar);

                        for (String number : numericalExpandedRange) {
                            maskedRange.add(number + maskingChar);
                        }
                    }
                } else {
                    maskedRange.addAll(numericalExpandedRange);
                }
            }

            unaryMaskingCharsIndex++;
        }

        return maskedRange;
    }

    /**
     * Removes all duplicate characters from a single mask point not to produce twin match tuples
     *
     * @param point String containing possible char duplicates
     * @return HashSet of a String
     */
    private String simplifyMaskPoint(String point) {
        return Arrays.stream(point.split(""))
                .distinct()
                .collect(Collectors.joining());
    }
}
