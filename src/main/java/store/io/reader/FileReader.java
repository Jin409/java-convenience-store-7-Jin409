package store.io.reader;

import static store.io.IoErrorMessages.OfFileReader.INVALID_DATA_FORMAT;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import store.handler.ErrorHandler;

public class FileReader {

    public static List<String[]> readMarkdownFile(String filePath, String delimiter) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            return parseFile(scanner, delimiter);
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        return new ArrayList<>();
    }

    private static List<String[]> parseFile(Scanner scanner, String delimiter) {
        List<String[]> lines = new ArrayList<>();
        int validSize = extractHeaderSize(scanner.nextLine(), delimiter);

        while (scanner.hasNextLine()) {
            String[] lineData = parseLine(scanner.nextLine(), delimiter);
            validateDataSize(lineData, validSize);
            lines.add(lineData);
        }

        return lines;
    }

    private static int extractHeaderSize(String headerLine, String delimiter) {
        return headerLine.split(delimiter).length;
    }

    private static String[] parseLine(String line, String delimiter) {
        return line.split(delimiter);
    }

    private static void validateDataSize(String[] lineData, int validSize) {
        if (lineData.length != validSize) {
            ErrorHandler.handle(new IllegalArgumentException(INVALID_DATA_FORMAT));
        }
    }
}