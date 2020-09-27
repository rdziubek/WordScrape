package pl.witampanstwa.wordscrape.report;

import pl.witampanstwa.wordscrape.report.structures.Document;
import pl.witampanstwa.wordscrape.structures.Boundary;
import pl.witampanstwa.wordscrape.structures.RowIntersection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReportWriter {
    private static final String FILE_NAME = "Report.html";

    public ReportWriter(List<RowIntersection> intersections,
                        List<String> sourceRows, List<String> targetRows,
                        List<Boundary> rowIntersectionsAtNumberRanges)
            throws IOException {
        bufferedWrite(new Document(
                intersections,
                sourceRows, targetRows,
                rowIntersectionsAtNumberRanges).getDocument());
    }

    public void bufferedWrite(String document)
            throws IOException {
        BufferedWriter out = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir") + "/" + FILE_NAME));
        System.out.println("Writing to: " + Paths.get(System.getProperty("user.dir") + "/" + FILE_NAME));
        out.write(document);

        out.close();
    }
}
