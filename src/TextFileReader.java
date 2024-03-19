import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//hi
public class TextFileReader
{
    private String directoryPath;

    // Constructor that takes in the file path as only parameter ("assets")
    public TextFileReader(String directoryPath)
    {
        this.directoryPath = directoryPath;
    }

    public void readTextFilesInDirectory()
    {
        // Creates a Path object representing the directory specified by directoryPath above
        Path directory = Paths.get(directoryPath);

        if (Files.exists(directory) && Files.isDirectory(directory))
        {
            try
            {
                Files.walk(directory)
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".txt"))

                        //it will iterate over the text files in the "assets" folder and calls
                        // the processFile method for each file.
                        .forEach(this::processFile);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else
        {
            System.err.println("Directory does not exist or is not a directory.");
        }
    }

    public void processFile(Path filePath)
    {
        try
        {
            // Read the content of the file as a string
            String content = Files.readString(filePath);

            String[] lines = content.split("\\r?\\n"); // Split content into lines


            // Display the file name and its content
            System.out.println("File: " + filePath.getFileName());

            // Will print out all the content of the files
            System.out.println(content);


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
