package pl.witampanstwa.wordscrape.report.structures;

import j2html.TagCreator;
import j2html.tags.Text;
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
                                                                sourceRows.get(intersection.getSourceIndex()),
                                                                intersection.getSourceModel()
                                                                        .getNumberMatchRanges()
                                                                        .get(0)
                                                                        .get(0),
                                                                intersection.getSourceModel()
                                                                        .getNumberMatchRanges()
                                                                        .get(intersection.getSourceModel()
                                                                                .getNumberMatchRanges()
                                                                                .size() - 1)
                                                                        .get(1)
                                                        ).getStyledContent(), td(new DocumentStyler(
                                                                targetRows.get(intersection.getTargetIndex()),
                                                                intersection.getTargetModel()
                                                                        .getNumberMatchRanges()
                                                                        .get(0)
                                                                        .get(0),
                                                                intersection.getTargetModel()
                                                                        .getNumberMatchRanges()
                                                                        .get(intersection.getTargetModel()
                                                                                .getNumberMatchRanges()
                                                                                .size() - 1)
                                                                        .get(1)
                                                        ).getStyledContent())))
                                                )
                                        )
                                )
                        )
                )
        );
    }
}
