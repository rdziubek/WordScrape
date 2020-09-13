package pl.witampanstwa;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Matcher {
    private final List<String> ascii_source_rows = new ArrayList<>();
    private final List<String> ascii_target_rows = new ArrayList<>();
    private List<String> source_buildings = new ArrayList<>();
    private List<String> target_buildings = new ArrayList<>();

    public Matcher(List<String> source, List<String> target) {
        for (String element : source) {
            this.ascii_source_rows.add(unidecode(element));
        }
        for (String element : target) {
            this.ascii_target_rows.add(unidecode(element));
        }

        System.out.println(ascii_source_rows);
        System.out.println(ascii_target_rows);
    }

    private String unidecode(String string) {
        return net.gcardone.junidecode.Junidecode.unidecode(string);
    }
}
