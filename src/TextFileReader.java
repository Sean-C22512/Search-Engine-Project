import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class TextFileReader
{
    // Private fields to hold file path, text file path, searched word,
    // result text, word counts, and number of results to display
    private String filePath;
    private String textFilePath = "";
    private String word;
    private StringBuilder resultText = new StringBuilder();
    private ArrayList<WordCount> wordCounts = new ArrayList<>();
    private int numberOfResults;


    // Constructor to initialise TextFileReader with file path, searched word
    // and number of results to be displayed
    public TextFileReader(String filePath, String word,int numberOfResults) {
        this.filePath = filePath;
        this.word = word;
        this.numberOfResults = numberOfResults;
    }

    // Method to amend file path by appending directory path.
    // Called when user wants to search specific text file
    public void amendFilePath() {
        textFilePath += Constants.DIRECTORY_PATH + "/" + filePath ;

    }

    // Method to get result text as a string
    public String getResultText() {
        return resultText.toString();
    }

    public void readAll_TextFiles() {

        // Creates a Path object representing the directory specified by directoryPath above
        Path directory = Paths.get(filePath);

        // Check if the directory exists and is a directory
        if (Files.exists(directory) && Files.isDirectory(directory)) {
            try
            {
                // Walk through the directory and process each text file
                Files.walk(directory)
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".txt"))
                        .forEach(path -> {

                            // Process the text file and get word count
                            WordCount wordCount = processFile(path);

                            // Ensure wordCount is not null
                            assert wordCount != null;

                            // Add wordCount to wordCounts if count is not zero
                            if (wordCount.getCount() != 0) {
                                wordCounts.add(wordCount);
                            }
                        });

                // Display results with specified number of results (specified in constructor)
                ResultsDisplay(numberOfResults);


            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Directory does not exist or is not a directory.");
        }
    }

    // Method to read a single text file
    public void read_TextFile() {

        // Get the path of the text file
        Path file = Paths.get(textFilePath);

        // Check if the file exists, is a regular file, and has a .txt extension
        if (Files.exists(file) && Files.isRegularFile(file) && file.toString().endsWith(".txt")) {

            // Process the text file and get word count
            WordCount wordCount = processFile(file);

            // Ensure wordCount is not null
            assert wordCount != null;

            // Add the wordCount to wordCounts ArrayList if count is not zero
            if (wordCount.getCount() != 0) {
                wordCounts.add(wordCount);
            }
            // Display results with specified number of results (specified in constructor)
            ResultsDisplay(numberOfResults);

        }

        else {
            System.err.println("File does not exist or is not a valid text file.");
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
                String token = scanner.next();
                token = token.replaceAll("[^a-zA-Z]", ""); // Only keeps letters
                if (token.equals(word)) {count++;}
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

    private void ResultsDisplay(int ResultsAmount)
    {
        // Sort the list based on the count in descending order
        wordCounts.sort(Comparator.comparingInt(WordCount::getCount).reversed());

        // Display the top x (ResultsAmount) entries
        int count = Math.min(ResultsAmount, wordCounts.size());


        if (wordCounts.isEmpty()) {
            resultText.append("No results found");
        } else {
            for (int i = 0; i < count; i++)
            {
                // retrieves the object stored at index i in the wordCounts ArrayList.
                // and assigns it to a variable called wordCount. Makes it easier to work with
                WordCount wordCount = wordCounts.get(i);
                resultText.append((i + 1) + ".) File: ").append(wordCount.getFileName()).append(", Count: ")
                        .append(wordCount.getCount()).append("\n");
            }
        }

    }

}
