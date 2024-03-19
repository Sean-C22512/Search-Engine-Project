import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TextFileReader {
    private String directoryPath;
    private String word;

    // Constructor that takes in the file path as only parameter ("assets")
    public TextFileReader(String directoryPath, String word) {
        this.directoryPath = directoryPath;
        this.word = word;
    }

    public void readTextFilesInDirectory() {
        // Creates a Path object representing the directory specified by directoryPath above
        Path directory = Paths.get(directoryPath);

        if (Files.exists(directory) && Files.isDirectory(directory)) {
            try {
                Files.walk(directory)
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".txt"))

                        //it will iterate over the text files in the "assets" folder and calls
                        // the processFile method for each file.
                        .forEach(this::processFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Directory does not exist or is not a directory.");
        }
    }

    // Searches for the word 'a' and prints the result
    private void processFile(Path filePath) {
        try {
            // Read the content of the file as a string
            String content = Files.readString(filePath);

            // Check if the content contains the word using football as example (will change to a user added string later on )
            if (content.contains(word)) {
                System.out.println("The word " + word + " is in the file: " + filePath.getFileName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

