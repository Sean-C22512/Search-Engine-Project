import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//kk

public class SearchGui implements ActionListener {
    private JLabel resultLabel;
    private JTextField input;

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
            // Simulating search process
            resultLabel.setText("Searching...");
            // Perform actual search here
            // Once search is done, update the resultLabel with the search result
            // For now, let's just update it with a sample result
            resultLabel.setText("Search complete!");
        }
    }


}
