package pl.witampanstwa.WordScrape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Produces ready lists of `Buildings`, provided according row records as input
 */
public class DataMatcher {
    private final List<Building> asciiSourceBuildings;
    private final List<Building> asciiTargetBuildings;
    private final Pattern REGEX_BUILDING_NUMBER =
            Pattern.compile("(?:^\\d+[.,]?)" +
                    "|(?:\\d{2,9}[,.]?\\d*\\s*m\\s*2?)" +
                    "|(\\d+[a-zA-Z]*)" +
                    "(?:[/\\\\]*\\d*)");
    private final Pattern REGEX_BUILDING_STREET =
            Pattern.compile("([A-Z]\\w*)" +
                    "[a-z \\t]*" +
                    "(?:\\d+[a-zA-Z]*[/\\\\]*\\d*)");
    private final Pattern REGEX_BUILDING_NUMBERS =
            Pattern.compile("(?:\\d{1,2}[. ](?:\\d{4}|\\d{2})[ ]?r[,.' ]?)" +
                    "|(?:(?:od|w|W) \\d{4})" +
                    "|(?:\\d+ [A-Z][A-Za-z]+)" +
                    "|(?:[137]-?go)" +
                    "|(?:[Ii]+\\.\\d{4})" +
                    "|(?:\\d{4}|\\d{2} ?r[,.' ]?)" +
                    "|(?:\\d{1,2}\\.\\d{1,2}\\.\\d{4})" +
                    "|(\\d+[A-Za-z]*[-/]?[Ii]*\\s?(?:abcd|abc|ab|a)?(?:[,a-d]*)?)");

    public DataMatcher(List<String> source, List<String> target) {
        List<String> asciiSourceRows = source.stream()
                .filter(Objects::nonNull)
                .map(this::unidecode)
                .collect(Collectors.toList());
        List<String> asciiTargetRows = target.stream()
                .filter(Objects::nonNull)
                .map(this::unidecode)
                .collect(Collectors.toList());

        asciiSourceBuildings = asciiSourceRows.stream()
                .map(row -> new Building(
                        findAll(REGEX_BUILDING_STREET, row, 1),
                        findAll(REGEX_BUILDING_NUMBERS, row, 1)))
                .collect(Collectors.toList());
        asciiTargetBuildings = asciiTargetRows.stream()
                .map(row -> new Building(
                        findAll(REGEX_BUILDING_STREET, row, 1),
                        findAll(REGEX_BUILDING_NUMBER, row, 1)))
                .collect(Collectors.toList());
    }

    public List<Building> getAsciiSourceBuildings() {
        return asciiSourceBuildings;
    }

    public List<Building> getAsciiTargetBuildings() {
        return asciiTargetBuildings;
    }

    private List<String> findAll(Pattern regex, String string, int group) {
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
