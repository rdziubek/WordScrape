package pl.witampanstwa;

public class Building {
    private final String street;
    private final int number;
    private final String numbers;

    public Building(String street, int number, String numbers) {
        this.street = street;
        this.number = number;
        this.numbers = numbers;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    public String getNumbers() {
        return numbers;
    }
}
