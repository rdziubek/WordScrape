package pl.witampanstwa.wordscrape;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

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

        for (RowIntersection row : dataParser.getIntersectedRows()) {
            System.out.println();
            System.out.println("Index in source: " + row.getSourceIndex());
            System.out.println("Index in target: " + row.getTargetIndex());
            System.out.println(row.getSourceModel().getStringStreets() + " " + row.getSourceModel().getStringNumbers());
            System.out.println(row.getTargetModel().getStringStreets() + " " + row.getTargetModel().getStringNumbers());
        }
    }
}
