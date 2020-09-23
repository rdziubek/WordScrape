package pl.witampanstwa.wordscrape.structures;

import java.util.List;

public class Building {
    private final List<String> streets;
    private final List<String> numbers;
    private final List<List<Integer>> streetMatchRanges;
    private final List<List<Integer>> numberMatchRanges;

    public Building(List<String> street, List<String> numbers, List<List<Integer>> streetMatchRanges, List<List<Integer>> numberMatchRanges) {
        this.streets = street;
        this.numbers = numbers;
        this.streetMatchRanges = streetMatchRanges;
        this.numberMatchRanges = numberMatchRanges;
    }

    public List<String> getStreets() {
        return streets;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public List<List<Integer>> getStreetMatchRanges() {
        return streetMatchRanges;
    }

    public List<List<Integer>> getNumberMatchRanges() {
        return numberMatchRanges;
    }
}
