package pl.witampanstwa.WordScrape;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scraper {
    private final List<String> tableRows = new ArrayList<>();

    public Scraper(String document_location) throws IOException {
        HWPFDocument document = new HWPFDocument(new POIFSFileSystem(new FileInputStream(document_location)));

        TableIterator iterator = new TableIterator(document.getRange());
        while (iterator.hasNext()) {
            Table table = iterator.next();
            for (int rowIndex = 0; rowIndex < table.numRows(); rowIndex++) {
                TableRow row = table.getRow(rowIndex);
                StringBuilder rowString = new StringBuilder();
                for (int colIndex = 0; colIndex < row.numCells(); colIndex++) {
                    TableCell cell = row.getCell(colIndex);
                    rowString.append(" ").append(cell.getParagraph(0).text().replace("\u0007", ""));
                }
                tableRows.add(rowString.toString().trim());
            }
        }
    }

    public List<String> getTableRows() {
        return Collections.unmodifiableList(tableRows);
    }
}
