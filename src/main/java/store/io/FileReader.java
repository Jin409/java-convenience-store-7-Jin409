package store.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import store.handler.ErrorHandler;

public class FileReader {

    public static List<String[]> readMarkdownFile(String filePath, String delimiter) {
        List<String[]> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            int validSize = getSizeOfKeys(scanner.nextLine(), delimiter);
            while (scanner.hasNextLine()) {
                String[] lineData = scanner.nextLine().split(delimiter);
                validateDataSize(lineData, validSize);
                lines.add(lineData);
            }
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
        return lines;
    }

    private static void validateDataSize(String[] lineData, int validSize) {
        if (lineData.length != validSize) {
            ErrorHandler.handle(new IllegalArgumentException("[ERROR] 데이터 형식이 올바르지 않습니다."));
        }
    }

    private static int getSizeOfKeys(String input, String delimiter) {
        return input.split(delimiter).length;
    }
}
