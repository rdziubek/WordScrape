package pl.witampanstwa.wordscrape.report.structures;

import j2html.TagCreator;
import pl.witampanstwa.wordscrape.report.DocumentStyler;
import pl.witampanstwa.wordscrape.report.ResourceFetcher;
import pl.witampanstwa.wordscrape.structures.RowIntersection;

import java.util.List;

import static j2html.TagCreator.*;

public class Document {
    private final String document;

    public Document(List<RowIntersection> intersections,
                    List<String> sourceRows, List<String> targetRows) {
        this.document = generateDocument(
                intersections,
                sourceRows, targetRows);
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
                                                each(intersections, intersection -> tr(td(new DocumentStyler(
                                                                sourceRows.get(intersection.getIndexItemLookedFor()),
                                                                intersection.getModelLookedFor()
                                                                        .getNumberMatchRanges()
                                                                        .get(0)
                                                                        .getBoundary().getLeft(),
                                                                intersection.getModelLookedFor()
                                                                        .getNumberMatchRanges()
                                                                        .get(intersection.getModelLookedFor()
                                                                                .getNumberMatchRanges()
                                                                                .size() - 1)
                                                                        .getBoundary().getRight(),
                                                                intersection.isWeak(),
                                                                intersection.wasInDoubt()
                                                        ).getStyledContent(), td(new DocumentStyler(
                                                                targetRows.get(intersection.getIndexItemLookedThrough()),
                                                                intersection.getModelLookedThrough()
                                                                        .getNumberMatchRanges()
                                                                        .get(0)
                                                                        .getBoundary().getLeft(),
                                                                intersection.getModelLookedThrough()
                                                                        .getNumberMatchRanges()
                                                                        .get(intersection.getModelLookedThrough()
                                                                                .getNumberMatchRanges()
                                                                                .size() - 1)
                                                                        .getBoundary().getRight(),
                                                                intersection.isWeak(),
                                                                intersection.wasInDoubt()
                                                        ).getStyledContent())))
                                                )
                                        )
                                )
                        )
                )
        );
    }
}
