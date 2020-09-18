package pl.witampanstwa.wordscrape;

import java.util.List;

public class UniqueBuildingMatcher {
    private final String streets;
    private final String numbers;
    private final boolean isInRange = false;
    private boolean doubtRaised = false;


    public UniqueBuildingMatcher(String streets, String numbers) {
        this.streets = streets;
        this.numbers = numbers;
    }

    public String getStreets() {
        return streets;
    }

    public String getNumbers() {
        return numbers;
    }

    public boolean isDoubtRaised() {
        return doubtRaised;
    }
}
