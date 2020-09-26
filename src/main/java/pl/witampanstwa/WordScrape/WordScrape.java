package pl.witampanstwa.wordscrape;

import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import pl.witampanstwa.wordscrape.report.ReportWriter;
import pl.witampanstwa.wordscrape.structures.Boundary;
import pl.witampanstwa.wordscrape.structures.RowIntersection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordScrape {
    public static void main(String[] args) throws FileNotFoundException {
        final String filePathItemsLookedFor = getPathLike("zleceni");
        final String filePathItemsLookedThrough = getPathLike("inwent");
        final List<String> rowsLookedFor = new ArrayList<>();
        final List<String> rowsLookedThrough = new ArrayList<>();

        try {
            rowsLookedFor.addAll(new Scraper(filePathItemsLookedFor)
                    .getTableRows());
            rowsLookedThrough.addAll(new Scraper(filePathItemsLookedThrough)
                    .getTableRows());
        } catch (OfficeXmlFileException | IOException e) {
            System.out.println("Error while fetching file/files form the filesystem. " + e);
        }

        DataMatcher dataMatcher = new DataMatcher(rowsLookedFor, rowsLookedThrough);
        DataParser dataParser = new DataParser(
                dataMatcher.getAsciiBuildingsLookedFor(),
                dataMatcher.getAsciiBuildingsLookedThrough()
        );

        for (RowIntersection intersection : dataParser.getIntersections()) {
            System.out.println();
            System.out.println("Source index: " + intersection.getIndexItemLookedFor());
            System.out.println("Target index: " + intersection.getIndexItemLookedThrough());
            System.out.println("Source row: " + rowsLookedFor.get(intersection.getIndexItemLookedFor()));
            System.out.println("Source model: " + intersection.getModelLookedFor().getStreets() + " "
                    + intersection.getModelLookedFor().getNumbers());
            System.out.println("Target row: " + rowsLookedThrough.get(intersection.getIndexItemLookedThrough()));
            System.out.println("Target model: " + intersection.getModelLookedThrough().getStreets() + " "
                    + intersection.getModelLookedThrough().getNumbers());
        }

        try {
            List<Boundary> rowIntersectionsAtNumberRanges = new ValueToIndexMapper(
                    dataParser.getUnaryFullyIntersectedNumberRanges(),
                    dataParser.getIntersections(),
                    rowsLookedThrough).getAbsoluteRangeBoundaries();

            ReportWriter reportWriter = new ReportWriter(dataParser.getIntersections(),
                    rowsLookedFor, rowsLookedThrough, rowIntersectionsAtNumberRanges);
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
        final String OFFICE_LOCKED_FILE_PREFIX = "~$";

        File f = new File(System.getProperty("user.dir"));
        File[] matchingFiles = f.listFiles((dir, name) -> unidecode(name)
                .toLowerCase()
                .contains(unidecode(partOfFilename))
                && !name.startsWith(OFFICE_LOCKED_FILE_PREFIX));

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
