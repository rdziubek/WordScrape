package pl.witampanstwa;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> wykaz = new ArrayList<>();
        List<String> zlecenie = new ArrayList<>();

        // TODO: Implement doc/docx switching (not via OfficeXmlFileException `file`)
        // TODO: Make location strings a single list in this scope here; requires deprecation of column-based matching
        try {
            wykaz = new Scraper("data/INWENTAR..doc")
                    .getTableRows();
            zlecenie = new Scraper("data/Załącznik do zlecenia jednostkowego 9.2018.doc")
                    .getTableRows();
        } catch (OfficeXmlFileException | IOException e) {
            System.out.println(e.toString());
        }

        Matcher matcher = new Matcher(wykaz, zlecenie);
    }
}
