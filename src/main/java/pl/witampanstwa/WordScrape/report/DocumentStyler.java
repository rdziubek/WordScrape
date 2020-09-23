package pl.witampanstwa.wordscrape.report;

import j2html.tags.ContainerTag;
import j2html.tags.DomContent;
import j2html.tags.Text;

import static j2html.TagCreator.span;

public class DocumentStyler {
    private static final String classMatchedRange = "matched_range";
    private final ContainerTag styledContent;

    public DocumentStyler(String bareContent, int startIndex, int endIndex) {
        styledContent = markConfidentAt(bareContent, startIndex, endIndex);
    }

    public DomContent getStyledContent() {
        return styledContent;
    }

    /**
     * Puts a styled span in between 2 indexes (both inclusive)
     * @param string
     * @param startIndex
     * @param endIndex
     * @return
     */
    private ContainerTag markConfidentAt(String string, int startIndex, int endIndex) {
        return new ContainerTag("").with(
                new Text(string.substring(0, startIndex)),
                span(string.substring(startIndex, endIndex)).withClass(classMatchedRange),
                new Text(string.substring(endIndex)));
    }
}
