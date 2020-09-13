package pl.witampanstwa;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "data/Załącznik do zlecenia jednostkowego 9.2018.doc";
        String fileName2 = "data/INWENTAR..doc";

        InputStream fis = new FileInputStream(fileName);
        POIFSFileSystem fs = new POIFSFileSystem(fis);
        HWPFDocument doc = new HWPFDocument(fs);

        Range range = doc.getRange();
        TableIterator itr = new TableIterator(range);
        while (itr.hasNext()) {
            Table table = itr.next();
            for (int rowIndex = 0; rowIndex < table.numRows(); rowIndex++) {
                TableRow row = table.getRow(rowIndex);
                for (int colIndex = 0; colIndex < row.numCells(); colIndex++) {
                    TableCell cell = row.getCell(colIndex);
                    System.out.println(cell.getParagraph(0).text());
                }
            }
        }
    }
}
