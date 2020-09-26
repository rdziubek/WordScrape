package pl.witampanstwa.wordscrape.structures;

import java.util.List;

/**
 * Stores extracted row nouns and their adjectives.
 */
public class Building {
    private final List<String> streets;
    private final List<String> numbers;
    private final List<Boundary> streetMatchBoundaries;
    private final List<Boundary> numberMatchBoundaries;

    public Building(List<String> street, List<String> numbers,
                    List<Boundary> streetMatchBoundaries, List<Boundary> numberMatchBoundaries) {
        this.streets = street;
        this.numbers = numbers;
        this.streetMatchBoundaries = streetMatchBoundaries;
        this.numberMatchBoundaries = numberMatchBoundaries;
    }

    public List<String> getStreets() {
        return streets;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public List<Boundary> getStreetMatchRanges() {
        return streetMatchBoundaries;
    }

    public List<Boundary> getNumberMatchRanges() {
        return numberMatchBoundaries;
    }
}
