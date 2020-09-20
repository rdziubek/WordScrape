package pl.witampanstwa.wordscrape.report;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class ResourceFetcher {
    private final URL resourceUri;
    private final String resourceContent;

    public ResourceFetcher(String name) {
        resourceUri = getClass().getClassLoader().getResource(name);
        InputStream in = getClass().getResourceAsStream(name);
        if (in != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            resourceContent = reader.lines().collect(Collectors.joining());
        } else {
            resourceContent = null;
        }
    }

    public URL getUri() {
        return resourceUri;
    }

    public String getContent() {
        return resourceContent;
    }
}
