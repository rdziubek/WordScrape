package pl.witampanstwa.wordscrape.report;

import j2html.tags.ContainerTag;
import j2html.tags.DomContent;
import j2html.tags.Text;

import static j2html.TagCreator.br;
import static j2html.TagCreator.span;

public class DocumentStyler {
    private static final String classMatchedRange = "matched_range";
    private static final String classWeakMatch = "weak_match";
    private static final String classDrunkIndicator = "drunk_indicator";
    private ContainerTag styledContent;

    public DocumentStyler(String bareContent,
                          int styleStart, int styleEnd,
                          boolean weak) {

        styledContent = markConfidenceAt(bareContent, styleStart, styleEnd);
        if (weak) {
            styledContent = markWeak(styledContent);
        }
    }

    public DomContent getStyledContent() {
        return styledContent;
    }

    /**
     * Puts a styled span in-between 2 indexes (both inclusive)
     *
     * @param string
     * @param startIndex
     * @param endIndex
     * @return
     */
    private ContainerTag markConfidenceAt(String string, int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex <= 0
                || startIndex >= endIndex
                || startIndex > string.length() - 1 || endIndex > string.length()) {
            return anonymousContainer(
                    new Text(string), br(),
                    span("Bardzo dziwny zakres! 😧").withClass(classDrunkIndicator));
        }

        return anonymousContainer(
                new Text(string.substring(0, startIndex)),
                span(string.substring(startIndex, endIndex)).withClass(classMatchedRange),
                new Text(string.substring(endIndex)));
    }

    private ContainerTag markWeak(ContainerTag container) {
        return new ContainerTag("").with(
                span(container).withClass(classWeakMatch)
        );
    }

    private ContainerTag anonymousContainer(DomContent... children) {
        return new ContainerTag("").with(children);
    }
}
