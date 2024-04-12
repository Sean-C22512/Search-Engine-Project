import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilterGui implements ActionListener {

    private JFrame filterFrame;
    private JComboBox<String> dropdown;
    private String[] filter_options = {"5", "10", "15"};
    private JLabel filterLabel;
    private String selectedItem;
    private JButton ok;


    public FilterGui() {
        filterFrame = new JFrame("FilterGui");
        filterFrame.setBounds(Constants.FILTER_X, Constants.FILTER_Y, Constants.FILTER_WIDTH, Constants.FRAME_HEIGHT);
        dropdown = new JComboBox<>(filter_options);
        dropdown.addActionListener(this); // Register the action listener
        filterFrame.setLayout(new FlowLayout()); // Set layout manager to FlowLayout
        filterFrame.setVisible(true);
        ok = new JButton("OK");

        filterLabel = new JLabel("Number of Results Displayed :");
        filterFrame.add(filterLabel);
        filterFrame.add(dropdown); // Add the dropdown to the frame
        filterFrame.add(ok);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dropdown) {
            String command = e.getActionCommand();
            if (command.equals("comboBoxChanged")) {
                selectedItem = (String) dropdown.getSelectedItem();
            }
        }
    }

    public int getFilterItem() {
        if (selectedItem != null && !selectedItem.isEmpty()) {

            return Integer.parseInt(selectedItem);
        }

        return 5;

    }
}