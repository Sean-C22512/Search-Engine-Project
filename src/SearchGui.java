import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Objects;

// This class provides a graphical user interface to search for text within files.
public class SearchGui implements ActionListener {
    // Swing Components

    // Strings for storing searched word and selected item
    private JFrame frame;
    private String searchedWord;
    private String selectedItem;

    // Instance of FilterGui
    private FilterGui filterGui;

    // JComboBox for dropdown selection
    private JComboBox<String> dropdown;

    // Array to hold options for the dropdown
    private String[] options = new String[Constants.MAX_SIZE]; // Options for the dropdown

    // JPanels for layout
    private JPanel contentPanel;
    private JPanel upPanel;
    private JPanel eastPanel;
    private JPanel midPanel;

    // JLabels for displaying text
    private JLabel searchLabel;
    private JLabel textFileLabel;
    private JLabel midTitle;

    // JTextArea for displaying results
    private JTextArea resultsTextArea;

    // JTextField for user input
    private JTextField searchText;

    // JButtons for interaction
    private JButton ok;
    private JButton reset;
    private JButton help;
    private JButton filter;
    private JButton about;
    private JButton darkModeToggle;

    // Boolean to track dark mode status
    private boolean darkMode = false;

    //Constructor initialises the GUI components and configures the frame.
    public SearchGui() {

        // Populates the dropdown with file choices
        populateDropdown();

        // Create a new JFrame with a title "Search Engine"
        frame = new JFrame("Search Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

        // Create a content panel with BorderLayout and add it to the frame
        contentPanel = new JPanel(new BorderLayout());
        frame.add(contentPanel);

        // Create an upper panel with a grid layout and add it to the content panel
        upPanel = new JPanel(new GridLayout(Constants.UP_ROW, Constants.UP_COL, Constants.H_GAP, Constants.V_GAP));
        upPanel.setBorder(new EmptyBorder(Constants.EMPTY_BORDER, Constants.EMPTY_BORDER, Constants.EMPTY_BORDER,
                Constants.EMPTY_BORDER));
        contentPanel.add(upPanel, BorderLayout.NORTH);

        // Create JLabels and JTextFields for search and dropdown selection, and add them to the upper panel
        searchLabel = new JLabel("Search:");
        searchText = new JTextField();
        textFileLabel = new JLabel("Text Files:");
        dropdown = new JComboBox<>(options);
        upPanel.add(searchLabel);
        upPanel.add(searchText);
        upPanel.add(textFileLabel);
        upPanel.add(dropdown);

        // Create a middle panel with BorderLayout and add it to the content panel
        midPanel = new JPanel(new BorderLayout());
        midPanel.setBorder(new EmptyBorder(Constants.EMPTY_BORDER,
                Constants.EMPTY_BORDER, Constants.EMPTY_BORDER,Constants.EMPTY_BORDER));
        contentPanel.add(midPanel, BorderLayout.CENTER);

        // Create a JLabel for displaying "Results", set font, and add it to the middle panel
        midTitle = new JLabel("Results");
        midTitle.setFont(new Font("Arial", Font.BOLD, Constants.MID_FONT_SIZE));
        midPanel.add(midTitle, BorderLayout.NORTH);

        // Create a JTextArea for displaying results, set rows and make it non-editable,
        // add it to a JScrollPane, and add the scroll pane to the middle pane
        resultsTextArea = new JTextArea("No results for now");
        resultsTextArea.setRows(Constants.RESULT_AREA_ROW);
        resultsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsTextArea);
        midPanel.add(scrollPane, BorderLayout.CENTER);

        // Create an east panel with a grid layout and add it to the content panel
        eastPanel = new JPanel(new GridLayout(Constants.EAST_ROW, Constants.EAST_COL, Constants.H_GAP, Constants.V_GAP));
        eastPanel.setBorder(new EmptyBorder(Constants.EMPTY_BORDER, Constants.EMPTY_BORDER, Constants.EMPTY_BORDER,
                Constants.EMPTY_BORDER));
        contentPanel.add(eastPanel, BorderLayout.EAST);

        // Create JButtons for actions,
        ok = new JButton("OK");
        reset = new JButton("Reset");
        help = new JButton("Help");
        filter = new JButton("Filter");
        about = new JButton("About");
        darkModeToggle = new JButton("Dark Mode");

        // Register action listeners for buttons
        ok.addActionListener(this);
        reset.addActionListener(this);
        help.addActionListener(this);
        filter.addActionListener(this);
        about.addActionListener(this);
        darkModeToggle.addActionListener(this);

        // Set initial button colors
        setButtonColor(ok, Color.GREEN);
        setButtonColor(reset, Color.RED);
        setButtonColor(help, Color.WHITE);
        setButtonColor(filter, Color.WHITE);
        setButtonColor(about, Color.WHITE);
        setButtonColor(darkModeToggle, Color.GRAY);

        // Added buttons to the east panel
        eastPanel.add(ok);
        eastPanel.add(reset);
        eastPanel.add(help);
        eastPanel.add(filter);
        eastPanel.add(about);
        eastPanel.add(darkModeToggle);

        // Configure keyboard shortcuts
        setupShortcuts();

        // Make the frame visible
        frame.setVisible(true);

    }
    // Handles actions performed on GUI components.
    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "OK":
                if (filterGui == null) {
                    JOptionPane.showMessageDialog(frame, "Please set filters first.");
                    return;
                }

                // Set "Searching..." in the results text area, get search text,
                // and selected text file form the dropdown menu
                resultsTextArea.setText("Searching...");
                searchedWord = searchText.getText();
                selectedItem = (String) dropdown.getSelectedItem();

                // Start a new thread to perform file reading operations
                new Thread(() -> {

                    // Create a TextFileReader instance with selected item, searched word, and filter items
                    TextFileReader textFileReader = new TextFileReader(selectedItem, searchedWord,
                            filterGui.getFilterItem());

                    // Check if selected item is equal to directory path (ALL)
                    if (Objects.equals(selectedItem, Constants.DIRECTORY_PATH))
                    {
                        // If directory path, read all text files in the directory
                        textFileReader.readAll_TextFiles();
                    }

                    else
                    {   // or else amend file path and read the text file
                        textFileReader.amendFilePath();
                        textFileReader.read_TextFile();
                    }

                    // Update the results text area with the result text
                    SwingUtilities.invokeLater(() -> {
                        resultsTextArea.setText(textFileReader.getResultText());
                    });
                }).start(); // Start the thread
                break;

            case "Reset":
                // Reset the GUI components to default state
                resetGui();
                break;

            case "Filter":
                // If filterGui is null then instantiate a new FilterGui object
                if (filterGui == null) {
                    filterGui = new FilterGui();
                }
                break;


            case "About":
                JOptionPane.showMessageDialog(frame, "This program searches for words and phrases in stored" +
                        " text files.\n Enter the chosen word/phrase into the search bar and press OK \nto run the" +
                        " program");
                break;

            case "Help":
                JOptionPane.showMessageDialog(frame, "No results found: There are no appearances of the" +
                        " searched word within the text files.\n\nShortcuts:\n- Ctrl + R: Reset\n- Ctrl + D: " +
                        "Dark Mode Toggle\n- Ctrl + H: Help\n- Ctrl + F: Filter\n- Ctrl + A: About");
                break;

            case "Dark Mode":
                // Toggle between dark and light themes
                toggleDarkMode();
                break;

            default:
                break;
        }
    }
    // Toggles the UI between dark and light mode.
    private void toggleDarkMode() {
        // Toggle dark mode
        darkMode = !darkMode;

        // Set background and foreground colors based on dark mode status
        Color background = darkMode ? Color.DARK_GRAY : null;
        Color foreground = darkMode ? Color.WHITE : null;

        // Set background colors for various panels and components
        frame.getContentPane().setBackground(background);
        contentPanel.setBackground(background);
        upPanel.setBackground(background);
        eastPanel.setBackground(background);
        midPanel.setBackground(background);

        // Set foreground colors for specific components
        searchLabel.setForeground(foreground);
        textFileLabel.setForeground(foreground);
        midTitle.setForeground(foreground);
        resultsTextArea.setForeground(foreground);
        resultsTextArea.setBackground(background);

        // Set background color for the dark mode toggle button
        darkModeToggle.setBackground(darkMode ? Color.LIGHT_GRAY : Color.GRAY);
    }

    // Set the color for buttons
    private void setButtonColor(JButton button, Color color) {
        button.setBackground(color);
        button.setOpaque(true);
    }

    // Populate the dropdown with file choices
    private void populateDropdown() {
        options[0] = Constants.DIRECTORY_PATH;
        for (int i = 2; i <= Constants.MAX_SIZE; i++) {
            options[i - 1] = i + ".txt";
        }
    }

    // Reset GUI components to their default states
    private void resetGui() {
        searchText.setText("");
        dropdown.setSelectedItem(Constants.DIRECTORY_PATH);
        resultsTextArea.setText("No results for now");
    }

    // Setup keyboard shortcuts for GUI operations
    private void setupShortcuts() {

        InputMap inputMap = contentPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = contentPanel.getActionMap();

        // Enter key to trigger search
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "searchPressed");
        actionMap.put("searchPressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionPerformed(new ActionEvent(ok, ActionEvent.ACTION_PERFORMED, "OK"));
            }
        });

        // Ctrl + D for dark mode toggle
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()),
                "darkModeToggle");
        actionMap.put("darkModeToggle", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darkModeToggle.doClick();
            }
        });

        // Ctrl + R for reset
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()),
                "reset");
        actionMap.put("reset", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset.doClick();
            }
        });

        // Ctrl + H for help
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_H, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()),
                "help");
        actionMap.put("help", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                help.doClick();
            }
        });

        // Ctrl + F for filter
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()),
                "filter");
        actionMap.put("filter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter.doClick();
            }
        });

        // Ctrl + A for about
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()),
                "about");
        actionMap.put("about", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                about.doClick();
            }
        });
    }
}
