import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String directoryPath = "assets";

        TextFileReader textFileReader = new TextFileReader(directoryPath);

        //textFileReader.readTextFilesInDirectory();

        SwingUtilities.invokeLater(SearchGui::new);


    }
}