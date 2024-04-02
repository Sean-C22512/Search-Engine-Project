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

/* ResultsTextField.setText("Searching...");

            // Save the entered text into the word variable
            SearchedWord = SearchText.getText();
            FileToSearch = (String) dropdown.getSelectedItem();
            FilePath = directoryPath + "/" + FileToSearch;*/