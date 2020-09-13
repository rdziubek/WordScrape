package pl.witampanstwa;

public class Building {
    private final String street;
    private final String number;
    private final String numbers;

    public Building(String street, String number, String numbers) {
        this.street = street;
        this.number = number;
        this.numbers = numbers;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getNumbers() {
        return numbers;
    }
}
