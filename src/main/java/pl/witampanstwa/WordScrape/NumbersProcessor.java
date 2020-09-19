package pl.witampanstwa.wordscrape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Analyses and decomposes single entry's numbers/ranges, where 'entry' is one data row/record.
 */
public class NumbersProcessor {
    private final List<String> processed;

    public NumbersProcessor(List<String> numbers) {
        this.processed = process(numbers);
    }

    public List<String> getProcessed() {
        return processed;
    }

    /**
     * Eliminates the possibility of omitting an exact number in a case `["2-4", "8", "14-20"]` by
     * filtering out ranges by building these on the fly and postponing for further processing
     * instead of doing such in-place.
     *
     * @param numbers building's number/numbers
     * @return expanded numbers
     */
    private List<String> process(List<String> numbers) {
        List<String> exactNumbers = new ArrayList<>();
        StringBuilder unaryRange = new StringBuilder();

        for (int currentAtomIndex = 0; currentAtomIndex < numbers.size(); currentAtomIndex++) {
            String currentAtom = numbers.get(currentAtomIndex);
            String previousAtom = (currentAtomIndex > 0 ? numbers.get(currentAtomIndex - 1) : null);

            if (currentAtom.endsWith("-") || previousAtom != null && previousAtom.endsWith("-")) {
                unaryRange.append(currentAtom);

                if (!currentAtom.endsWith("-")) {
                    exactNumbers.addAll(decomposeRangeIntoExacts(unaryRange.toString()));
                    unaryRange = new StringBuilder();
                }
            } else {
                exactNumbers.add(currentAtom);
            }
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
     *
     * @param numericalExpandedRange
     * @param mask
     * @return
     */
    private List<String> applyACharMaskVerbosely(List<String> numericalExpandedRange, List<String> mask) {
        List<String> maskedRange = new ArrayList<>();

        int currentMaskingChar = 0;
        for (String maskingChar : mask) {
            String previousMaskingChar = (currentMaskingChar > 0 ? mask.get(currentMaskingChar - 1) : null);
            if (!maskingChar.equals(previousMaskingChar)) {
                for (String number : numericalExpandedRange) {
                    maskedRange.add(number + maskingChar);
                }
            }

            currentMaskingChar++;
        }

        return maskedRange;
    }
}
