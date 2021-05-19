package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
    static public List<Double> readFile(String directory, String filename) {
        List<Double> res = new ArrayList<>();
        Path filePath = Path.of(directory, filename);
        try(final BufferedReader fileReader = Files.newBufferedReader(filePath)) {
            String line = fileReader.readLine();
            if (line != null) {
                res = Arrays.stream(line.split(" "))
                        .map(Double::parseDouble)
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            System.err.println("Error while reading file " + filename);
            System.err.println(e.getMessage());
        }
        return res;
    }

    public static <T> void writeFile(String directory, String filename, List<T> list) {
        Path filePath = Path.of(directory, filename);
        try(final BufferedWriter fileWriter = Files.newBufferedWriter(filePath)) {
            for (T element : list) {
                fileWriter.write(element + " ");
            }
        } catch (IOException e) {
            System.err.println("Error while writing file " + filename);
            System.err.println(e.getMessage());
        }
    }
}
