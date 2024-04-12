/*
    Class to represent the Word Count of a specific File
*/

public class WordCount {
    private String fileName;
    private int count;

    public WordCount(String fileName, int count) {
        this.fileName = fileName;
        this.count = count;
    }

    public String getFileName() {
        return fileName;
    }

    public int getCount() {
        return count;
    }
}
