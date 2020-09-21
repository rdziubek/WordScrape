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
            XWPFDocument doc = new XWPFDocument(
                    new FileInputStream(
                            new File(documentLocation)));
            List<XWPFTable> tables = doc.getTables();

            for (XWPFTable table : tables) {
                for (XWPFTableRow row : table.getRows()) {
                    StringBuilder rowString = new StringBuilder();
                    for (XWPFTableCell cell : row.getTableCells()) {
                        rowString.append(" ")
                                .append(ringThatBellOut(
                                        cell.getText()));
                    }
                    tableRows.add(rowString.toString().trim());
                }
            }
        } else if (documentLocation.endsWith("doc")) {
            HWPFDocument document = new HWPFDocument(
                    new POIFSFileSystem(
                            new FileInputStream(documentLocation)));
            TableIterator iterator = new TableIterator(document.getRange());

            while (iterator.hasNext()) {
                Table table = iterator.next();
                for (int rowIndex = 0; rowIndex < table.numRows(); rowIndex++) {
                    TableRow row = table.getRow(rowIndex);

                    StringBuilder rowString = new StringBuilder();
                    for (int colIndex = 0; colIndex < row.numCells(); colIndex++) {
                        rowString.append(" ")
                                .append(ringThatBellOut(
                                        row.getCell(colIndex).getParagraph(0).text()));
                    }
                    tableRows.add(rowString.toString().trim());
                }
            }
        } else {
            throw new FileNotFoundException("Found none of either .doc or .docx files!");
        }
    }

    /**
     * Word documents can contain a bell control-char placed after a carriage return, thus this method removes these.
     *
     * @param ringingText Word document's content / part of content
     * @return document's passed in text without bell control-chars
     */
    private String ringThatBellOut(String ringingText) {
        return ringingText.replace("\u0007", "");
    }

    public List<String> getTableRows() {
        return Collections.unmodifiableList(tableRows);
    }
}
