// https://zenodo.org/records/3360392/files/D184MB.zip?download=1
// link to download text files

public class Main {
    public static void main(String[] args) {
        String directoryPath = "assets";

        TextFileReader textFileReader = new TextFileReader(directoryPath);

        textFileReader.readTextFilesInDirectory();




    }
}