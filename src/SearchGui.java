import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;


public class SearchGui implements ActionListener {


    private JFrame frame;
    private String SearchedWord;
    private String selectedItem;

    private JComboBox<String> dropdown;
    private String[] options = new String[Constants.MAX_SIZE];

    private JPanel upPanel;
    private JPanel eastPanel;
    private JPanel midPanel;

    private JLabel SearchLabel;
    private JLabel TextFileLabel;
    private JLabel MidTitle;
    private JTextArea ResultsTextArea;

    private JTextField SearchText;

    private JButton ok;
    private JButton reset;
    private JButton help;
    private JButton filter;
    private JButton about;
    private JButton darkModeToggle; // New button for dark mode

    private boolean darkMode = false;

    /**
     * Constructor to initialize the GUI components.
     */
    public SearchGui() {
        populateDropdown();

        // Initialize frame and panels
        frame = new JFrame("Search Engine");
        upPanel = new JPanel();
        eastPanel = new JPanel();
        midPanel = new JPanel();

        // Initialize labels, text area, and text field
        SearchLabel = new JLabel("Search:");
        TextFileLabel = new JLabel("Text Files : ");
        MidTitle = new JLabel("Results");
        ResultsTextArea = new JTextArea("No results for now");
        SearchText = new JTextField();

        // Initialize buttons
        ok = new JButton("OK");
        reset = new JButton("Reset");
        help = new JButton("Help");
        filter = new JButton("Filter");
        about = new JButton("About");
        darkModeToggle = new JButton("Dark Mode"); // Initialize dark mode button

        // Add action listeners to buttons and text field
        ok.addActionListener(this);
        SearchText.addActionListener(this);
        dropdown.addActionListener(this);
        reset.addActionListener(this);
        filter.addActionListener(this);
        about.addActionListener(this);
        help.addActionListener(this);
        darkModeToggle.addActionListener(this); // Add action listener for dark mode button

        // Set button colors
        setButtonColor(ok, Color.GREEN);
        setButtonColor(reset, Color.RED);
        setButtonColor(help, Color.WHITE);
        setButtonColor(filter, Color.WHITE);
        setButtonColor(about, Color.WHITE);
        setButtonColor(darkModeToggle, Color.GRAY); // Custom color for dark mode button

        // Configure panels with layout and add components
        upPanel.setLayout(new GridLayout(Constants.UP_ROW, Constants.UP_COL));
        upPanel.add(SearchLabel);
        upPanel.add(SearchText);
        upPanel.add(TextFileLabel);
        upPanel.add(dropdown);

        eastPanel.setLayout(new GridLayout(Constants.EAST_ROW, Constants.EAST_COL)); // Increased grid size for accommodating dark mode button
        eastPanel.add(ok);
        eastPanel.add(reset);
        eastPanel.add(help);
        eastPanel.add(filter);
        eastPanel.add(about);
        eastPanel.add(darkModeToggle); // Add dark mode button

        midPanel.setLayout(new BorderLayout(Constants.MID_H_GAP,Constants.MID_V_GAP));
        midPanel.add(MidTitle, BorderLayout.NORTH);
        midPanel.add(new JScrollPane(ResultsTextArea), BorderLayout.CENTER);
        // Configure main frame and add panels
        frame.setBounds(Constants.FRAME_X, Constants.FRAME_Y, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(upPanel, BorderLayout.NORTH);
        frame.getContentPane().add(midPanel, BorderLayout.CENTER);
        frame.getContentPane().add(eastPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }

    /**
     * ActionPerformed method to handle button clicks and text field input.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "OK":
                ResultsTextArea.setText("Searching...");
                SearchedWord = SearchText.getText();
                selectedItem = (String) dropdown.getSelectedItem();


                // Execute search operation on a separate thread
                new Thread(() -> {

                    TextFileReader textFileReader = new TextFileReader(selectedItem, SearchedWord);

                    if(Objects.equals(selectedItem, Constants.DIRECTORY_PATH))
                    {
                        textFileReader.readAll_TextFiles();

                    }

                    else{
                        textFileReader.amendFilePath();
                        textFileReader.read_TextFile();
                    }


                    SwingUtilities.invokeLater(() -> {
                        ResultsTextArea.setText(textFileReader.getResultText());
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
                // Display about message when "about" button is clicked
                JOptionPane.showMessageDialog(frame, "This program searches for words and phrases in stored text files.\n Enter the choosen word/phrase into the search bar and press ok \nto run the program");
                break;

            case "Help":
                JOptionPane.showMessageDialog(frame, "no resules found = there are no appearances of the searched word within the text files");
                break;

            case "Dark Mode": // Toggle dark mode
                toggleDarkMode();
                break;

            default:
                // Handle other cases if needed
                break;
        }
    }

    /**
     * Method to toggle dark mode.
     */
    private void toggleDarkMode() {
        darkMode = !darkMode;
        if (darkMode) {
            // Set dark mode colors
            frame.getContentPane().setBackground(Color.DARK_GRAY);
            upPanel.setBackground(Color.DARK_GRAY);
            eastPanel.setBackground(Color.DARK_GRAY);
            midPanel.setBackground(Color.DARK_GRAY);
            SearchLabel.setForeground(Color.WHITE);
            TextFileLabel.setForeground(Color.WHITE);
            MidTitle.setForeground(Color.WHITE);
            ResultsTextArea.setForeground(Color.WHITE);
            ResultsTextArea.setBackground(Color.DARK_GRAY);

            // Set custom dark mode button color
            darkModeToggle.setBackground(Color.LIGHT_GRAY);
        } else {
            // Set light mode colors
            frame.getContentPane().setBackground(null);
            upPanel.setBackground(null);
            eastPanel.setBackground(null);
            midPanel.setBackground(null);
            SearchLabel.setForeground(null);
            TextFileLabel.setForeground(null);
            MidTitle.setForeground(null);
            ResultsTextArea.setForeground(null);
            ResultsTextArea.setBackground(null);

            // Reset dark mode button color
            darkModeToggle.setBackground(Color.GRAY);
        }
    }

    /**
     * Method to set the background color of the buttons.
     */
    private void setButtonColor(JButton button, Color color) {
        button.setBackground(color);
        button.setOpaque(true);
    }

    /**
     * Method to populate the dropdown with file options.
     */
    private void populateDropdown() {
        options[0] = Constants.DIRECTORY_PATH;
        for (int i = 2; i <= Constants.MAX_SIZE; i++) {
            options[i - 1] = i + ".txt";
        }
        dropdown = new JComboBox<>(options);
    }

    /**
     * Method to reset GUI components.
     */
    private void resetGui() {
        SearchText.setText("");
        dropdown.setSelectedItem(Constants.DIRECTORY_PATH);
        ResultsTextArea.setText("No results for now");
    }

    /**
     * Main method to instantiate and display the GUI.
     */

}
