package pl.witampanstwa.wordscrape.report;

import pl.witampanstwa.wordscrape.report.structures.Document;
import pl.witampanstwa.wordscrape.structures.RowIntersection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReportWriter {
    private final String fileName = "Report.html";

    public ReportWriter(List<RowIntersection> intersections, List<String> sourceRows, List<String> targetRows) {
        try {
            bufferedWrite(new Document(intersections, sourceRows, targetRows).getDocument());
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void bufferedWrite(String document)
            throws IOException {
        BufferedWriter out = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir") + "/" + fileName));
        System.out.println("Wiritng to: " + Paths.get(System.getProperty("user.dir") + "/" + fileName));
        out.write(document);

        out.close();
    }
}
