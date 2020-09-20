package pl.witampanstwa.wordscrape;

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

    public String getStringStreets() {
        return String.join(", ", streets);
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public String getStringNumbers() {
        return String.join(", ", numbers);
    }
}
