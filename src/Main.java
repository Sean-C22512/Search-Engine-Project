import javax.swing.*;

public class Main {
    public static void main(String[] args) {
       

        //textFileReader.readTextFilesInDirectory();

        textFileReader.readTextFilesInDirectory();

        SwingUtilities.invokeLater(SearchGui::new);

    }
}