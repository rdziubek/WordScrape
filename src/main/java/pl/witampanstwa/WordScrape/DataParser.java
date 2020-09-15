package pl.witampanstwa.WordScrape;

import java.util.List;

public class DataParser {
    private final boolean isInRange;
    private boolean doubtRaised = false;
    private int whatIndexMatchedAt;
    private int whereIndexMatchedAt;

    public DataParser(List<List<String>> itemsLookedFor, List<List<String>> itemsLookedIn) {
        this.isInRange = isStreetInRange(itemsLookedFor, itemsLookedIn)
                && isNumberInRange(itemsLookedFor, itemsLookedIn);
    }

    private boolean isNumberInRange(List<List<String>> what, List<List<String>> where) {
        return false;
    }

    // TODO: adopt this to fit either 1- or 2-dimensional lists; similar needs to be done in `isNumberInRange`.
    private boolean isStreetInRange(List<List<String>> what, List<List<String>> where) {
        for (List<String> streets : what) {
            for (String street : streets) {
                if (where.contains(street)) {
                    return true;
                }
            }
        }

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
