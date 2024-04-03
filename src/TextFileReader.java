import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class TextFileReader {
    private String directoryPath;
    private String word;
    StringBuilder resultText = new StringBuilder();

    public String getResultText() {
        return resultText.toString();

    }

    // Constructor that takes in the file path as only parameter ("assets")
    public TextFileReader(String directoryPath, String word) {
        this.directoryPath = directoryPath;
        this.word = word;
    }

    public void readTextFilesInDirectory() {
        // Creates a Path object representing the directory specified by directoryPath above
        Path directory = Paths.get(directoryPath);

        // initializes an ArrayList named wordCounts that is capable of holding objects of type WordCount
        ArrayList<WordCount> wordCounts = new ArrayList<>();

        if (Files.exists(directory) && Files.isDirectory(directory)) {
            try
            {
                Files.walk(directory)
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".txt"))
                        .forEach(path -> {
                            WordCount wordCount = processFile(path);
                            if (wordCount != null) {
                                wordCounts.add(wordCount);
                            }
                        });

                // Sort the list based on the count in descending order
                wordCounts.sort(Comparator.comparingInt(WordCount::getCount).reversed());

                // Display the top 10 entries
                int count = Math.min(10, wordCounts.size());
                for (int i = 0; i < count; i++)
                {
                    // retrieves the object stored at index i in the wordCounts ArrayList.
                    // and assigns it to a variable called wordCount. Makes it easier to work with
                    WordCount wordCount = wordCounts.get(i);
                    resultText.append((i+1)+".) File: ").append(wordCount.getFileName()).append(", Count: ").append(wordCount.getCount()).append("\n");

                }


            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Directory does not exist or is not a directory.");
        }
    }


    private WordCount processFile(Path filePath)
    {
        try
        {
            // Read the content of the file as a string
            String content = Files.readString(filePath);

            // Create a Scanner to tokenize the content
            Scanner scanner = new Scanner(content);
            int count = 0;

            // Loop through each token in the content
            while (scanner.hasNext())
            {
                // Check if the token matches the specified word
                if (scanner.next().equals(word)) {
                    // Increment the count if the word is found
                    count++;
                }
            }

            // Close the Scanner
            scanner.close();

            return new WordCount(filePath.getFileName().toString(), count);

        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
