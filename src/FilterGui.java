import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilterGui implements ActionListener {

    // Filter Gui Swing Components
    private JFrame filterFrame;
    private JComboBox<String> dropdown;
    private String[] filter_options = {"5", "10", "15"};
    private JLabel filterLabel;
    private String selectedItem;
    private JButton ok;


    public FilterGui() {

        // Create a new JFrame for the FilterGui
        filterFrame = new JFrame("FilterGui");

        // Set bounds for the filter frame
        filterFrame.setBounds(Constants.FILTER_X, Constants.FILTER_Y, Constants.FILTER_WIDTH, Constants.FILTER_HEIGHT);

        // Set layout manager to FlowLayout
        filterFrame.setLayout(new FlowLayout());

        // Make the filter frame visible
        filterFrame.setVisible(true);

        // Create JComboBox for filter options and register action listener
        dropdown = new JComboBox<>(filter_options);
        dropdown.addActionListener(this);

        // Create JButton for confirming filter selection
        ok = new JButton("OK");

        // Create JLabel for displaying filter label
        filterLabel = new JLabel("Number of Results Displayed :");

        // Add components to the filter frame
        filterFrame.add(filterLabel);
        filterFrame.add(dropdown);
        filterFrame.add(ok);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dropdown) {
            String command = e.getActionCommand();

            // Check if the action is a combo box change event
            if (command.equals("comboBoxChanged")) {
                // Get the selected item from the dropdown
                selectedItem = (String) dropdown.getSelectedItem();
            }
        }
    }

    public int getFilterItem() {
        // Check if selectedItem is not null or empty
        if (selectedItem != null && !selectedItem.isEmpty()) {

            // Parse the selectedItem to an integer and return it
            return Integer.parseInt(selectedItem);
        }

        // Return default filter value if selectedItem is null or empty
        return 5;

    }
}