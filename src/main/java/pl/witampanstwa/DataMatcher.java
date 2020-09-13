package pl.witampanstwa;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataMatcher {
    private final Pattern REGEX_BUILDING_NUMBER_WITH_FLAT_OR_NOT = Pattern.compile("(?:^\\d+[.,]?)|(?:\\d{2,9}[,.]?\\d*\\s*m\\s*2?)|(\\d+[a-zA-z]*[/\\\\]*\\d*)");
    private final Pattern REGEX_BUILDING_NUMBER = Pattern.compile("(?:[/\\\\]\\d+)|(\\d+[a-zA-z]*)");
    private final List<String> ascii_source_rows = new ArrayList<>();
    private final List<String> ascii_target_rows = new ArrayList<>();
    private List<Building> ascii_source_buildings = new ArrayList<>();
    private List<Building> ascii_target_buildings = new ArrayList<>();

    public DataMatcher(List<String> source, List<String> target) {
        for (String element : source) {
            this.ascii_source_rows.add(unidecode(element));
        }
        for (String element : target) {
            this.ascii_target_rows.add(unidecode(element));
        }

        for (String row : ascii_source_rows) {
            this.ascii_source_buildings.add(new Building("street_holder", "number_holder", "numbers_holder"));
        }
        for (String row : ascii_target_rows) {
            System.out.println('\n' + row);
            Matcher numberFlatMatcher = REGEX_BUILDING_NUMBER_WITH_FLAT_OR_NOT.matcher(row);
            List<String> numberMatches = new ArrayList<>();
            List<String> numberFlatMatches = new ArrayList<>();
            while (numberFlatMatcher.find()) {
                String flatMatch = numberFlatMatcher.group(1);
                if (flatMatch != null) {
                    Matcher numberMatcher = REGEX_BUILDING_NUMBER.matcher(flatMatch);
                    while (numberMatcher.find()){
                        String match = numberMatcher.group(1);
                        if (match != null) {
                            numberMatches.add(match);
                        }
                    }
                    numberFlatMatches.add(flatMatch);
                }
            }
            this.ascii_target_buildings.add(
                    new Building(
                            "street_holder",
                            numberMatches.get(0),
                            "numbers_holder"));
            System.out.println(numberFlatMatches.toString());
        }

        for (Building b : ascii_target_buildings) {
            System.out.println();
            System.out.println(b);
            System.out.println(b.getStreet());
            System.out.println(b.getNumber());
            System.out.println(b.getNumbers());
        }
    }

    private String unidecode(String string) {
        return net.gcardone.junidecode.Junidecode.unidecode(string);
    }
}
