package pl.witampanstwa.wordscrape.report;

import j2html.tags.ContainerTag;
import j2html.tags.DomContent;
import j2html.tags.Text;
import pl.witampanstwa.wordscrape.structures.Type;

import static j2html.TagCreator.*;

public class DocumentStyler {
    private static final String CLASS_MATCHED_RANGE = "matched_range";
    private static final String CLASS_DRUNK_INDICATOR = "drunk_indicator";
    private static final String CLASS_SELECTION_BAR = "selection_bar";
    private ContainerTag styledContent;

    public DocumentStyler(String bareContent,
                          int styleStart, int styleEnd,
                          boolean weak, Type contentType) {

        styledContent = markConfidenceAt(bareContent, styleStart, styleEnd);
        if (weak && contentType == Type.LOOKED_FOR) {
            styledContent = markWeak(styledContent);
        }

        styledContent = insertSelectionBar(styledContent);
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
                    span("Bardzo dziwny zakres! 😧").withClass(CLASS_DRUNK_INDICATOR));
        }

        return anonymousContainer(
                new Text(string.substring(0, startIndex)),
                span(string.substring(startIndex, endIndex)).withClass(CLASS_MATCHED_RANGE),
                new Text(string.substring(endIndex)));
    }

    private ContainerTag markWeak(ContainerTag container) {
        return anonymousContainer(
                container, br(),
                span("Niedokładnie! 🤔").withClass(CLASS_DRUNK_INDICATOR)
        );
    }

    private ContainerTag insertSelectionBar(ContainerTag container) {
        return anonymousContainer(
                container,
                div().withClass(CLASS_SELECTION_BAR)
        );
    }

    private ContainerTag anonymousContainer(DomContent... children) {
        return new ContainerTag("").with(children);
    }
}
