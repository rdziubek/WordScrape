package pl.witampanstwa.wordscrape.structures;

import java.util.List;

public class Building {
    private final List<String> streets;
    private final List<String> numbers;

    public Building(List<String> street, List<String> numbers) {
        this.streets = street;
        this.numbers = numbers;
    }

    public List<String> getStreets() {
        return streets;
    }

    public List<String> getNumbers() {
        return numbers;
    }
}
