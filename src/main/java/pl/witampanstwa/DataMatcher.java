package pl.witampanstwa;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataMatcher {
    private final List<String> asciiSourceRows = new ArrayList<>();
    private final List<String> asciiTargetRows = new ArrayList<>();
    private final List<Building> asciiSourceBuildings = new ArrayList<>();
    private final List<Building> asciiTargetBuildings = new ArrayList<>();
    private final Pattern REGEX_BUILDING_NUMBER = Pattern.compile("(?:^\\d+[.,]?)|(?:\\d{2,9}[,.]?\\d*\\s*m\\s*2?)|(\\d+[a-zA-Z]*)(?:[/\\\\]*\\d*)");
    private final Pattern REGEX_BUILDING_STREET = Pattern.compile("([A-Z]\\w*)[a-z \\t]*(?:\\d+[a-zA-Z]*[/\\\\]*\\d*)");
    private final Pattern REGEX_BUILDING_NUMBERS = Pattern.compile("(?:\\d{1,2}[. ](?:\\d{4}|\\d{2})[ ]?r[,.' ]?)|(?:(?:od|w|W) \\d{4})|(?:\\d+ [A-Z][A-Za-z]+)|(?:[137]-?go)|(?:[Ii]+\\.\\d{4})|(?:\\d{4}|\\d{2} ?r[,.' ]?)|(?:\\d{1,2}\\.\\d{1,2}\\.\\d{4})|(\\d+[A-Za-z]*[-/]?[Ii]*\\s?(?:abcd|abc|ab|a)?(?:[,a-d]*)?)");

    public DataMatcher(List<String> source, List<String> target) {
        source.stream()
                .filter(Objects::nonNull)
                .forEach(element -> this.asciiSourceRows.add(unidecode(element)));
        target.stream()
                .filter(Objects::nonNull)
                .forEach(element -> this.asciiTargetRows.add(unidecode(element)));

        asciiSourceRows.forEach(row -> this.asciiSourceBuildings.add(
                new Building(
                        String.join("", findall(REGEX_BUILDING_STREET, row, 1)),
                        findall(REGEX_BUILDING_NUMBERS, row, 1))));

        asciiTargetRows.forEach(row -> this.asciiTargetBuildings.add(
                new Building(
                        String.join("", findall(REGEX_BUILDING_STREET, row, 1)),
                        findall(REGEX_BUILDING_NUMBER, row, 1))));
    }

    private List<String> findall(Pattern regex, String string, int group) {
        List<String> matches = new ArrayList<>();
        Matcher matcher = regex.matcher(string);

        while (matcher.find()) {
            String match = matcher.group(group);
            if (match != null) {
                matches.add(match);
            }
        }
        return matches;
    }

    private String unidecode(String string) {
        return net.gcardone.junidecode.Junidecode.unidecode(string);
    }
}
