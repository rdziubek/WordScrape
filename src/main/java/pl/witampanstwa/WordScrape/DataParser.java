package pl.witampanstwa.WordScrape;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Processes collected objects / collections of objects
 */
public class DataParser {
    private final boolean isInRange;
    private boolean doubtRaised = false;
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
                .map(Building::getStreet)
                .collect(Collectors.toList());
        List<List<String>> numbersLookedThrough = itemsLookedThrough.stream()
                .map(Building::getStreet)
                .collect(Collectors.toList());

        this.isInRange = isStreetInRange(streetsLookedFor, streetsLookedThrough)
                && isNumberInRange(numbersLookedFor, numbersLookedThrough);
    }

    private boolean isNumberInRange(List<List<String>> what, List<List<String>> where) {
        int currentPosition = 0;
        return false;
    }

    // TODO: adopt this to fit either 1- or 2-dimensional lists; similar needs to be done in `isNumberInRange`.
    private boolean isStreetInRange(List<List<String>> what, List<List<String>> where) {
        int currentPosition = 0;
        return false;
    }

    public boolean isInRange() {
        return isInRange;
    }

    public boolean isDoubtRaised() {
        return doubtRaised;
    }

    public int getWhatIndexMatchedAt() {
        return whatIndexMatchedAt;
    }

    public int getWhereIndexMatchedAt() {
        return whereIndexMatchedAt;
    }
}
