package pl.witampanstwa.wordscrape;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import pl.witampanstwa.wordscrape.report.ReportWriter;
import pl.witampanstwa.wordscrape.structures.RowIntersection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordScrape {
    public static void main(String[] args) throws FileNotFoundException {
        final String sourceFilePath = getPathLike("zleceni");
        final String targetFilePath = getPathLike("inwent");
        final List<String> sourceRows = new ArrayList<>();
        final List<String> targetRows = new ArrayList<>();

        try {
            sourceRows.addAll(new Scraper(sourceFilePath)
                    .getTableRows());
            targetRows.addAll(new Scraper(targetFilePath)
                    .getTableRows());
        } catch (OfficeXmlFileException | IOException e) {
            System.out.println("Error while fetching file/files form the filesystem. " + e);
        }

        DataMatcher dataMatcher = new DataMatcher(sourceRows, targetRows);
        DataParser dataParser = new DataParser(
                dataMatcher.getAsciiSourceBuildings(),
                dataMatcher.getAsciiTargetBuildings());

        for (RowIntersection intersection : dataParser.getIntersections()) {
            System.out.println();
            System.out.println("Source index: " + intersection.getSourceIndex());
            System.out.println("Target index: " + intersection.getTargetIndex());
            System.out.println("Source row: " + sourceRows.get(intersection.getSourceIndex()));
            System.out.println("Source model: " + intersection.getSourceModel().getStreets() + " "
                    + intersection.getSourceModel().getNumbers());
            System.out.println("Target row: " + targetRows.get(intersection.getTargetIndex()));
            System.out.println("Target model: " + intersection.getTargetModel().getStreets() + " "
                    + intersection.getTargetModel().getNumbers());
        }

        try {
            ReportWriter reportWriter = new ReportWriter(dataParser.getIntersections(), sourceRows, targetRows);
        } catch (IOException e) {
            System.out.println("Cannot write to filesystem. " + e);
        }
    }

    /**
     * Performs an ASCII lowercase partial check for files in the current working dir.
     *
     * @param partOfFilename case-insensitive regex-alike part of a filename, converted to ASCII
     * @return full file URI
     */
    private static String getPathLike(String partOfFilename) throws FileNotFoundException {
        File f = new File(System.getProperty("user.dir"));
        File[] matchingFiles = f.listFiles((dir, name) -> unidecode(name)
                .toLowerCase()
                .contains(unidecode(partOfFilename)));

        if (matchingFiles != null) {
            for (File matchingFile : matchingFiles) {
                if (matchingFile != null) {
                    System.out.println("getting the file for: " + partOfFilename
                            + " with result: " + matchingFile.getPath());
                    return matchingFile.getPath();
                }
            }
        }
        throw new FileNotFoundException("No applicable file/files found! (i.e. in the working dir)");
    }

    private static String unidecode(String string) {
        return net.gcardone.junidecode.Junidecode.unidecode(string);
    }
}
