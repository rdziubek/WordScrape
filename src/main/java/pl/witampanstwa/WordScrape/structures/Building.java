package pl.witampanstwa.wordscrape.structures;

import java.util.List;

/**
 * Stores extracted row nouns and their adjectives.
 */
public class Building {
    private final List<String> streets;
    private final List<String> numbers;
    private final List<Range> streetMatchRanges;
    private final List<Range> numberMatchRanges;

    public Building(List<String> street, List<String> numbers,
                    List<Range> streetMatchRanges, List<Range> numberMatchRanges) {
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

    public List<Range> getStreetMatchRanges() {
        return streetMatchRanges;
    }

    public List<Range> getNumberMatchRanges() {
        return numberMatchRanges;
    }
}
