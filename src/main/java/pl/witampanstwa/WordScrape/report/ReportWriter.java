package pl.witampanstwa.wordscrape.report;

import pl.witampanstwa.wordscrape.structures.RowIntersection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportWriter {
    private final String fileName = "Report.html";
//    private final List<Pair<String, String>> intersectedRowPairs;

    public ReportWriter(List<RowIntersection> intersections, List<String> sourceRows, List<String> targetRows) {

//        intersectedRowPairs = new Pair<String, String>();
//        for (RowIntersection intersection : intersections) {
//            sourceRows.get(intersection.getSourceIndex());
//        }

        try {
            bufferedWrite(new Document(intersections, sourceRows, targetRows).getDocument());
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void bufferedWrite(String document)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(document);

        writer.close();
    }
}
