package pl.witampanstwa.wordscrape;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import pl.witampanstwa.wordscrape.report.ReportWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordScrape {
    public static void main(String[] args) {
        final List<String> sourceRows = new ArrayList<>();
        final List<String> targetRows = new ArrayList<>();

        // TODO: Implement doc/docx switching (not via OfficeXmlFileException `file`)
        // TODO: Make location strings a single list in this scope here; requires full deprecation of column-based matching
        try {
            sourceRows.addAll(new Scraper("data/Załącznik do zlecenia jednostkowego 9.2018.doc")
                    .getTableRows());
            targetRows.addAll(new Scraper("data/INWENTAR..doc")
                    .getTableRows());
        } catch (OfficeXmlFileException | IOException e) {
            System.out.println(e.toString());
        }

        DataMatcher dataMatcher = new DataMatcher(sourceRows, targetRows);
        DataParser dataParser = new DataParser(
                dataMatcher.getAsciiSourceBuildings(),
                dataMatcher.getAsciiTargetBuildings());

        for (RowIntersection intersection : dataParser.getIntersections()) {
            System.out.println();
            System.out.println("Source index: " + intersection.getSourceIndex());
            System.out.println("Target index: " + intersection.getTargetIndex());
            System.out.println("Source row: " + sourceRows.get(intersection.getSourceIndex()));
            System.out.println("Source model: " + intersection.getSourceModel().getStreets() + " " + intersection.getSourceModel().getNumbers());
            System.out.println("Target row: " + targetRows.get(intersection.getTargetIndex()));
            System.out.println("Target model: " + intersection.getTargetModel().getStreets() + " " + intersection.getTargetModel().getNumbers());
        }

        ReportWriter reportWriter = new ReportWriter(dataParser.getIntersections(), sourceRows, targetRows);
    }
}
