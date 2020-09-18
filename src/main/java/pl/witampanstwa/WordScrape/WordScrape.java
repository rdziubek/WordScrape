package pl.witampanstwa.wordscrape;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordScrape {
    public static void main(String[] args) {
        List<String> sourceRows = new ArrayList<>();
        List<String> targetRows = new ArrayList<>();

        // TODO: Implement doc/docx switching (not via OfficeXmlFileException `file`)
        // TODO: Make location strings a single list in this scope here; requires full deprecation of column-based matching
        try {
            sourceRows = new Scraper("data/Załącznik do zlecenia jednostkowego 9.2018.doc")
                    .getTableRows();
            targetRows = new Scraper("data/INWENTAR..doc")
                    .getTableRows();
        } catch (OfficeXmlFileException | IOException e) {
            System.out.println(e.toString());
        }

        DataMatcher dataMatcher = new DataMatcher(sourceRows, targetRows);
        DataParser dataParser = new DataParser(
                dataMatcher.getAsciiSourceBuildings(),
                dataMatcher.getAsciiTargetBuildings());
    }
}
