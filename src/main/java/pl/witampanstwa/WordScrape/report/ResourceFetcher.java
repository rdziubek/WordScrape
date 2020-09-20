package pl.witampanstwa.wordscrape.report;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ResourceFetcher {
    private final String resource;

    public ResourceFetcher(String name) {
        InputStream in = getClass().getResourceAsStream(name);
        if (in != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            resource = reader.lines().collect(Collectors.joining());
        } else {
            resource = null;
        }
    }

    public String getResource() {
        return resource;
    }
}
