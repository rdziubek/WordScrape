package pl.witampanstwa.wordscrape;

import pl.witampanstwa.wordscrape.structures.Building;
import pl.witampanstwa.wordscrape.structures.IntTuple;
import pl.witampanstwa.wordscrape.structures.Range;

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
    private final List<Building> asciiBuildingsLookedFor;
    private final List<Building> asciiBuildingsLookedThrough;
    private final Pattern REGEX_BUILDING_NUMBER =
            Pattern.compile("(?:^ ?\\d+[.,]?(?:\\d*))" +
                    "|(?:(?:\\d+[.])?\\d{2,9}[,.]?\\d*\\s*m\\s*2?)" +
                    "|(\\d+[a-zA-Z]*)" +
                    "(?:[/\\\\]*\\d*)");
    private final Pattern REGEX_BUILDING_STREET =
            Pattern.compile("((?:[137]-?go\\s*)?(?:\\d+ )?[A-Za-z]\\w*)" +
                    "[ \\t]*" +
                    "(?:\\d+[a-zA-Z]*)(?:[/\\\\]+\\d*)");
    private final Pattern REGEX_BUILDING_NUMBERS =
            Pattern.compile("(?:\\d{1,2}[. ](?:\\d{4}|\\d{2}) ?r[,.' ]?)" +
                    "|(?:(?:od|w|W) \\d{4})" +
                    "|(?:\\d+ [A-Z][A-Za-z]+)" +
                    "|(?:[137]-?go)" +
                    "|(?:[Ii]+\\.\\d{4})" +
                    "|(?:\\d{4}|\\d{2} ?r[,.' ]?)" +
                    "|(?:\\d{1,2}\\.\\d{1,2}\\.\\d{4})" +
                    "|(\\d+[A-Za-z]*[-/]?[Ii]*\\s?(?:abcd|abc|ab|a)?(?:[,a-d]*)?)");
    private final Pattern REGEX_BUILDING_NUMBERS_STREET =
            Pattern.compile("(?:\\d{1,2}[. ](?:\\d{4}|\\d{2}) ?r[,.' ]?)" +
                    "|(?:(?:od|w|W) \\d{4})" +
                    "|(?:[Ii]+\\.\\d{4})" +
                    "|(?:\\d{4}|\\d{2} ?r[,.' ]?)" +
                    "|(?:\\d{1,2}\\.\\d{1,2}\\.\\d{4})" +
                    "|((?:[137]-?go\\s*)?(?:\\d+ )?[A-Za-z]\\w*)" +
                    "\\s*" +
                    "(?:\\d+[A-Za-z]*[-/]?[Ii]*\\s?(?:abcd|abc|ab|a)?(?:[,a-d]*)?)"
            );

    public DataMatcher(List<String> source, List<String> target) {
        List<String> asciiSourceRows = source.stream()
                .filter(Objects::nonNull)
                .map(this::unidecode)
                .collect(Collectors.toList());
        List<String> asciiTargetRows = target.stream()
                .filter(Objects::nonNull)
                .map(this::unidecode)
                .collect(Collectors.toList());

        asciiBuildingsLookedFor = asciiSourceRows.stream()
                .map(row -> new Building(
                        findAll(REGEX_BUILDING_STREET, row, 1),
                        findAll(REGEX_BUILDING_NUMBER, row, 1),
                        findAllMatchRanges(REGEX_BUILDING_STREET, row, 1),
                        findAllMatchRanges(REGEX_BUILDING_NUMBER, row, 1)))
                .collect(Collectors.toList());
        asciiBuildingsLookedThrough = asciiTargetRows.stream()
                .map(row -> new Building(
                        findAll(REGEX_BUILDING_NUMBERS_STREET, row, 1),
                        findAll(REGEX_BUILDING_NUMBERS, row, 1),
                        findAllMatchRanges(REGEX_BUILDING_NUMBERS_STREET, row, 1),
                        findAllMatchRanges(REGEX_BUILDING_NUMBERS, row, 1)))
                .collect(Collectors.toList());
    }

    public List<Building> getAsciiBuildingsLookedFor() {
        return asciiBuildingsLookedFor;
    }

    public List<Building> getAsciiBuildingsLookedThrough() {
        return asciiBuildingsLookedThrough;
    }

    private List<String> findAll(Pattern regex, String string, int group) {
        List<String> matches = new ArrayList<>();
        Matcher matcher = regex.matcher(string);

        while (matcher.find()) {
            String match = matcher.group(group);
            if (match != null) {
                matches.add(match.replaceAll("[ ,.;:]", ""));
            }
        }
        return matches;
    }

    /**
     * Returns each atom's left (start) and right (end) match boundaries.
     * Match end needs be matched based on the truncated atom length.
     *
     * @param regex
     * @param string
     * @param group
     * @return
     */
    private List<Range> findAllMatchRanges(Pattern regex, String string, int group) {
        List<Range> matchRanges = new ArrayList<>();
        Matcher matcher = regex.matcher(string);

        while (matcher.find()) {
            String match = matcher.group(group);
            if (match != null) {
                int matchStart = matcher.start(group);
                int matchEnd = matchStart + match.replaceAll("[ ,.;:]", "").length();

                matchRanges.add(new Range(new IntTuple(matchStart, matchEnd)));
            }
        }
        return matchRanges;
    }

    private String unidecode(String string) {
        return net.gcardone.junidecode.Junidecode.unidecode(string);
    }
}
