package pl.witampanstwa.wordscrape.report.structures;

import j2html.TagCreator;
import pl.witampanstwa.wordscrape.report.ResourceFetcher;
import pl.witampanstwa.wordscrape.structures.RowIntersection;

import java.util.List;

import static j2html.TagCreator.*;
import static j2html.TagCreator.td;

public class Document {
    private final String document;

    public Document(List<RowIntersection> intersections,
                    List<String> sourceRows, List<String> targetRows) {
        this.document = generateDocument(intersections, sourceRows, targetRows);
    }

    public String getDocument() {
        return document;
    }

    private String generateDocument(List<RowIntersection> intersections,
                                    List<String> sourceRows, List<String> targetRows) {
        return document(
                html(
                        head(
                                title("Intersekcje"),
                                meta().withCharset("UTF-8"),
                                TagCreator.style(new ResourceFetcher("/css/main.css").getContent())
                        ),
                        body(
                                table(
                                        tbody(
                                                tr(
                                                        th("Znalezione"),
                                                        th("Przeszukiwane")
                                                ),
                                                each(intersections, intersection -> tr(
                                                        td(sourceRows.get(intersection.getSourceIndex())),
                                                        td(targetRows.get(intersection.getTargetIndex()))
                                                ))
                                        )
                                )
                        )
                )
        );
    }
}
