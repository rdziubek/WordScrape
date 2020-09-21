package pl.witampanstwa.wordscrape;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scraper {
    private final List<String> tableRows = new ArrayList<>();

    public Scraper(String documentLocation) throws IOException {
        if (documentLocation.endsWith("docx")) {
            File file = new File(documentLocation);
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument doc = new XWPFDocument(fis);
            List<XWPFTable> tables = doc.getTables();

            for (XWPFTable table : tables) {
                for (XWPFTableRow row : table.getRows()) {
                    StringBuilder rowString = new StringBuilder();
                    for (XWPFTableCell cell : row.getTableCells()) {
                        rowString.append(" ").append(cell.getText().replace("\u0007", ""));
                    }
                    tableRows.add(rowString.toString().trim());
                }
            }
        } else if (documentLocation.endsWith("doc")) {
            HWPFDocument document = new HWPFDocument(new POIFSFileSystem(new FileInputStream(documentLocation)));
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
    }

    public List<String> getTableRows() {
        return Collections.unmodifiableList(tableRows);
    }
}
