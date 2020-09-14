package pl.witampanstwa;

import java.util.List;

public class Building {
    private final String street;
    private final List<String> numbers;

    public Building(String street, List<String> numbers) {
        this.street = street;
        this.numbers = numbers;
    }

    public String getStreet() {
        return street;
    }

    public List<String> getNumbers() {
        return numbers;
    }
}
