import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchGui implements ActionListener {
    private JLabel resultLabel;
    private JTextField input;
    private String directoryPath = "assets";
    private String word;

    public SearchGui() {
        JFrame frame = new JFrame("Search Engine");
        JPanel panel = new JPanel();
        JButton searchButton = new JButton("Search");
        input = new JTextField(20);
        resultLabel = new JLabel();

        searchButton.addActionListener(this);
        input.addActionListener(this);

        panel.add(input);
        panel.add(searchButton);
        panel.add(resultLabel);

        frame.add(panel);
        frame.setSize(300, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Search")) {
            resultLabel.setText("Searching...");

            // Save the entered text into the word variable
            word = input.getText();

            // Execute the time-consuming task on a separate thread
            new Thread(() -> {
                TextFileReader textFileReader = new TextFileReader(directoryPath, word);
                textFileReader.readTextFilesInDirectory();

                // Update the result label on the EDT
                SwingUtilities.invokeLater(() -> {
                    resultLabel.setText("Search complete!");
                });
            }).start();
        }
    }

}
