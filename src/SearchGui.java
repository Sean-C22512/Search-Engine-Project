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
    //private String FileToSearch;
    //private String FilePath;

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


    public SearchGui() {

        populateDropdown();

        frame = new JFrame("Search Engine");

        upPanel = new JPanel();
        eastPanel = new JPanel();
        midPanel = new JPanel();

        SearchLabel = new JLabel("Search:");
        TextFileLabel = new JLabel("Text Files : ");
        MidTitle = new JLabel("Results");
        ResultsTextArea = new JTextArea("No results for now");

        SearchText = new JTextField();

        // Buttons
        ok = new JButton("OK");
        reset = new JButton("Reset");
        help = new JButton("Help");
        //JButton advanced = new JButton("Advanced...");

        ok.addActionListener(this);
        SearchText.addActionListener(this);
        dropdown.addActionListener(this);
        reset.addActionListener(this);

        upPanel.setLayout(new GridLayout(2,3));
        upPanel.add(SearchLabel);
        upPanel.add(TextFileLabel);
        upPanel.add(SearchText);
        upPanel.add(dropdown);

        eastPanel.setLayout(new GridLayout(2,3));
        eastPanel.add(ok); eastPanel.add(reset); eastPanel.add(help); //eastPanel.add(advanced);

        midPanel.setLayout(new BorderLayout(0, 0));
        midPanel.add(MidTitle);
        midPanel.add(ResultsTextArea);
        midPanel.setBorder(new TitledBorder(null, "Result", TitledBorder.LEADING,
                TitledBorder.TOP, Font.getFont("Arial"), Color.BLUE));

        frame.setBounds(100, 100, 450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(upPanel, BorderLayout.NORTH);
        frame.getContentPane().add(midPanel, BorderLayout.WEST);
        frame.getContentPane().add(eastPanel, BorderLayout.EAST);
        frame.setVisible(true);


    }
    private void populateDropdown() {
        // Populate options array with filenames from "2.txt" to "499.txt"

        options[0] = "All";
        for (int i = 2; i <= MAX_SIZE; i++)
        {
            options[i-1] = i + ".txt";
        }

        dropdown = new JComboBox<>(options);
    }

    private void ResetGui()
    {
        SearchText.setText("");
        dropdown.setSelectedItem("All");
        ResultsTextArea.setText("No results for now");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK"))
        {
            ResultsTextArea.setText("Searching...");

            // Save the entered text into the word variable
            SearchedWord = SearchText.getText();

            //FileToSearch = (String) dropdown.getSelectedItem();
            //FilePath = directoryPath + "/" + FileToSearch;



            // Execute the time-consuming task on a separate thread
            new Thread(() ->
            {
                TextFileReader textFileReader = new TextFileReader(directoryPath, SearchedWord);
                textFileReader.readTextFilesInDirectory();

                // Update the result label on the EDT
                SwingUtilities.invokeLater(() ->
                {
                    ResultsTextArea.setText(textFileReader.getResultText());
                });
            }).start();

        } // end if .equals("OK"))

        else if (e.getActionCommand().equals("Reset"))
        {
            ResetGui();

        }
    }

}
