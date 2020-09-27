package pl.witampanstwa.wordscrape.report.structures;

import j2html.TagCreator;
import pl.witampanstwa.wordscrape.report.DocumentStyler;
import pl.witampanstwa.wordscrape.report.ResourceFetcher;
import pl.witampanstwa.wordscrape.structures.Boundary;
import pl.witampanstwa.wordscrape.structures.RowIntersection;

import java.util.List;

import static j2html.TagCreator.*;

public class Document {
    private final String document;

    public Document(List<RowIntersection> intersections,
                    List<String> sourceRows, List<String> targetRows,
                    List<Boundary> rowIntersectionsAtNumberRanges) {

        this.document = generateDocument(
                intersections,
                sourceRows, targetRows,
                rowIntersectionsAtNumberRanges);
    }

    public String getDocument() {
        return document;
    }

    private String generateDocument(List<RowIntersection> intersections,
                                    List<String> sourceRows, List<String> targetRows,
                                    List<Boundary> rowIntersectionsAtNumberRanges) {
        // TODO: Refactor dynamic allocation into static.
        for (int i = 0; i < intersections.size(); i++) {
            intersections.get(i).setUnaryIntersectedNumberRanges(rowIntersectionsAtNumberRanges.get(i));
        }

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
                                                                        .getIndices().getLeft(),
                                                                intersection.getModelLookedFor()
                                                                        .getNumberMatchRanges()
                                                                        .get(intersection.getModelLookedFor()
                                                                                .getNumberMatchRanges()
                                                                                .size() - 1)
                                                                        .getIndices().getRight(),
                                                                intersection.isWeak()
                                                        ).getStyledContent(), td(new DocumentStyler(
                                                                targetRows.get(intersection.getIndexItemLookedThrough()),
                                                                intersection.getUnaryIntersectedNumberRanges()
                                                                        .getIndices().getLeft(),
                                                                intersection.getUnaryIntersectedNumberRanges()
                                                                        .getIndices().getRight(),
                                                                intersection.isWeak()
                                                        ).getStyledContent())))
                                                )
                                        )
                                )
                        )
                )
        );
    }
}
