package darth.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class TextUtils {
    public void save(Path path, String string) throws IOException {
        Files.writeString(path, string, StandardCharsets.UTF_8);
    }

    public String load(Path path) throws FileNotFoundException {
        File textFile = new File(path.toString());

        Scanner scanner = new Scanner((new FileReader(textFile)));

        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine() + "\n");
        }

        return sb.toString();

    }
}
