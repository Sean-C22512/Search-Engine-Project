import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchGui implements ActionListener {
    private JFrame frame;

    private String directoryPath = "assets";
    private String word;

    private JPanel upPanel;
    private JPanel eastPanel;
    private JPanel midPanel;

    private JLabel SearchLabel;
    private JLabel TextFileLabel;
    private JLabel MidTitle;
    private JLabel ResultsTextField;

    private JTextField SearchText;
    private JTextField TextFileOption;

    private JButton ok;
    private JButton cancel;
    private JButton help;


    public SearchGui() {
        frame = new JFrame("Search Engine");

        upPanel = new JPanel();
        eastPanel = new JPanel();
        midPanel = new JPanel();

        SearchLabel = new JLabel("Search:");
        TextFileLabel = new JLabel("Text Files : ");
        MidTitle = new JLabel("Results");

        // Text Fields
        ResultsTextField = new JLabel("No results for now");
        SearchText = new JTextField();
        TextFileOption = new JTextField();

        // Buttons
        ok = new JButton("OK");
        cancel = new JButton("Reset");
        help = new JButton("Help");
        //JButton advanced = new JButton("Advanced...");

        ok.addActionListener(this);
        SearchText.addActionListener(this);

        upPanel.setLayout(new GridLayout(2,3));
        upPanel.add(SearchLabel);
        upPanel.add(TextFileLabel);
        upPanel.add(SearchText);
        upPanel.add(TextFileOption);

        eastPanel.setLayout(new GridLayout(2,3));
        eastPanel.add(ok); eastPanel.add(cancel); eastPanel.add(help); //eastPanel.add(advanced);

        midPanel.setLayout(new BorderLayout(0, 0));
        midPanel.add(MidTitle);
        midPanel.add(ResultsTextField);
        midPanel.setBorder(new TitledBorder(null, "Result", TitledBorder.LEADING,
                TitledBorder.TOP, Font.getFont("Arial"), Color.BLUE));

        frame.setBounds(100, 100, 450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(upPanel, BorderLayout.NORTH);
        frame.getContentPane().add(midPanel, BorderLayout.WEST);
        frame.getContentPane().add(eastPanel, BorderLayout.EAST);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")) {
            ResultsTextField.setText("Searching...");

            // Save the entered text into the word variable
            word = SearchText.getText();

            // Execute the time-consuming task on a separate thread
            new Thread(() -> {
                TextFileReader textFileReader = new TextFileReader(directoryPath, word);
                textFileReader.readTextFilesInDirectory();

                // Update the result label on the EDT
                SwingUtilities.invokeLater(() -> {
                    ResultsTextField.setText("Search complete!");
                });
            }).start();
        }
    }

}
