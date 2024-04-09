import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class SearchGui implements ActionListener {

    private JFrame frame;
    private String searchedWord;
    private String selectedItem;

    private JComboBox<String> dropdown;
    private String[] options = new String[Constants.MAX_SIZE];

    private JPanel searchPanel;
    private JPanel resultPanel;

    private JLabel searchLabel;
    private JLabel textFileLabel;
    private JLabel midTitle;
    private JTextArea resultsTextArea;

    private JTextField searchText;

    private JButton okButton;
    private JButton resetButton;
    private JButton helpButton;
    private JButton filterButton;
    private JButton aboutButton;
    private JButton darkModeToggle;

    private boolean darkMode = false;

    public SearchGui() {
        populateDropdown();

        frame = new JFrame("Search Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Initialize panels
        searchPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Initialize labels, text area, and text field
        searchLabel = new JLabel("Search:");
        textFileLabel = new JLabel("Text Files:");
        midTitle = new JLabel("Results");
        resultsTextArea = new JTextArea("No results for now");
        resultsTextArea.setEditable(false);

        searchText = new JTextField();

        // Initialize buttons
        okButton = createButton("OK", Color.GREEN);
        resetButton = createButton("Reset", Color.RED);
        helpButton = createButton("Help", Color.WHITE);
        filterButton = createButton("Filter", Color.WHITE);
        aboutButton = createButton("About", Color.WHITE);
        darkModeToggle = createButton("Dark Mode", Color.GRAY);

        // Add action listeners to buttons and text field
        okButton.addActionListener(this);
        searchText.addActionListener(this);
        dropdown.addActionListener(this);
        resetButton.addActionListener(this);
        filterButton.addActionListener(this);
        aboutButton.addActionListener(this);
        helpButton.addActionListener(this);
        darkModeToggle.addActionListener(this);

        // Add components to search panel
        searchPanel.add(searchLabel);
        searchPanel.add(searchText);
        searchPanel.add(textFileLabel);
        searchPanel.add(dropdown);

        // Add components to result panel
        resultPanel.add(midTitle, BorderLayout.NORTH);
        resultPanel.add(new JScrollPane(resultsTextArea), BorderLayout.CENTER);

        // Add components to frame
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(searchPanel, BorderLayout.NORTH);
        frame.getContentPane().add(resultPanel, BorderLayout.CENTER);
        frame.getContentPane().add(createButtonPanel(), BorderLayout.EAST);
        frame.setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 5, 10));
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.add(okButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(helpButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(aboutButton);
        buttonPanel.add(darkModeToggle);
        return buttonPanel;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setOpaque(true);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "OK":
                resultsTextArea.setText("Searching...");
                searchedWord = searchText.getText();
                selectedItem = (String) dropdown.getSelectedItem();

                // Execute search operation on a separate thread
                new Thread(() -> {
                    TextFileReader textFileReader = new TextFileReader(selectedItem, searchedWord);

                    if (Objects.equals(selectedItem, Constants.DIRECTORY_PATH)) {
                        textFileReader.readAll_TextFiles();
                    } else {
                        textFileReader.amendFilePath();
                        textFileReader.read_TextFile();
                    }

                    SwingUtilities.invokeLater(() -> {
                        resultsTextArea.setText(textFileReader.getResultText());
                    });
                }).start();
                break;

            case "Reset":
                resetGui();
                break;

            case "Filter":
                FilterGui filterGui = new FilterGui();
                break;

            case "About":
                JOptionPane.showMessageDialog(frame, "This program searches for words and phrases in stored text files.\n Enter the choosen word/phrase into the search bar and press ok \nto run the program");
                break;

            case "Help":
                JOptionPane.showMessageDialog(frame, "no results found = there are no appearances of the searched word within the text files");
                break;

            case "Dark Mode":
                toggleDarkMode();
                break;

            default:
                break;
        }
    }

    private void toggleDarkMode() {
        darkMode = !darkMode;
        if (darkMode) {
            frame.getContentPane().setBackground(Color.DARK_GRAY);
            searchPanel.setBackground(Color.DARK_GRAY);
            resultPanel.setBackground(Color.DARK_GRAY);
            searchLabel.setForeground(Color.WHITE);
            textFileLabel.setForeground(Color.WHITE);
            midTitle.setForeground(Color.WHITE);
            resultsTextArea.setForeground(Color.WHITE);
            resultsTextArea.setBackground(Color.DARK_GRAY);
            darkModeToggle.setBackground(Color.LIGHT_GRAY);
        } else {
            frame.getContentPane().setBackground(null);
            searchPanel.setBackground(null);
            resultPanel.setBackground(null);
            searchLabel.setForeground(null);
            textFileLabel.setForeground(null);
            midTitle.setForeground(null);
            resultsTextArea.setForeground(null);
            resultsTextArea.setBackground(null);
            darkModeToggle.setBackground(Color.GRAY);
        }
    }

    private void populateDropdown() {
        options[0] = Constants.DIRECTORY_PATH;
        for (int i = 2; i <= Constants.MAX_SIZE; i++) {
            options[i - 1] = i + ".txt";
        }
        dropdown = new JComboBox<>(options);
    }

    private void resetGui() {
        searchText.setText("");
        dropdown.setSelectedItem(Constants.DIRECTORY_PATH);
        resultsTextArea.setText("No results for now");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SearchGui::new);
    }
}
