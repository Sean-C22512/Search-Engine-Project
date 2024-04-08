import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchGui implements ActionListener {

    private static final int MAX_SIZE = 499;

    private JFrame frame;
    private String directoryPath = "assets";
    private String SearchedWord;

    private JComboBox<String> dropdown;
    private String[] options = new String[MAX_SIZE];

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
    private JButton info;

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
        info = new JButton("Info"); // Added the "Info" button

        // Add action listeners to buttons and text field
        ok.addActionListener(this);
        SearchText.addActionListener(this);
        dropdown.addActionListener(this);
        reset.addActionListener(this);
        filter.addActionListener(this);
        info.addActionListener(this); // Add ActionListener to the "Info" button

        // Configure panels with layout and add components
        upPanel.setLayout(new GridLayout(2, 2)); // Adjusted to 2 columns
        upPanel.add(SearchLabel);
        upPanel.add(SearchText);
        upPanel.add(TextFileLabel);
        upPanel.add(dropdown);

        eastPanel.setLayout(new GridLayout(5, 1)); // Changed to 5 rows (buttons) vertically
        eastPanel.add(ok);
        eastPanel.add(reset);
        eastPanel.add(help);
        eastPanel.add(filter);
        eastPanel.add(info); // Added the "Info" button

        midPanel.setLayout(new BorderLayout(0, 0));
        midPanel.add(MidTitle, BorderLayout.NORTH);
        midPanel.add(new JScrollPane(ResultsTextArea), BorderLayout.CENTER);
        midPanel.setBorder(new TitledBorder(null, "Result", TitledBorder.LEADING,
                TitledBorder.TOP, Font.getFont("Arial"), Color.BLUE));

        // Configure main frame and add panels
        frame.setBounds(100, 100, 450, 350);
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

                // Execute search operation on a separate thread
                new Thread(() -> {
                    TextFileReader textFileReader = new TextFileReader(directoryPath, SearchedWord);
                    textFileReader.readTextFilesInDirectory();

                    // Update GUI with search results
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

            case "Info":
                // Display info message when "Info" button is clicked
                JOptionPane.showMessageDialog(frame, "This is the Info button.");
                break;

            default:
                // Handle other cases if needed
                break;
        }
    }

    /**
     * Method to populate the dropdown with file options.
     */
    private void populateDropdown() {
        options[0] = "All";
        for (int i = 2; i <= MAX_SIZE; i++) {
            options[i - 1] = i + ".txt";
        }
        dropdown = new JComboBox<>(options);
    }

    /**
     * Method to reset GUI components.
     */
    private void resetGui() {
        SearchText.setText("");
        dropdown.setSelectedItem("All");
        ResultsTextArea.setText("No results for now");
    }

    /**
     * Main method to instantiate and display the GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SearchGui());
    }
}
