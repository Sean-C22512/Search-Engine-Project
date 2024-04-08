import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilterGui implements ActionListener
{
    // Check Box variables :
    private JFrame CheckFrame;
    private JCheckBox CheckBox_1;
    private JCheckBox CheckBox_2;

    public FilterGui()
    {
        CheckFrame = new JFrame("CheckBox");
        CheckBox_1 = new JCheckBox("5 results");
        CheckBox_2 = new JCheckBox("10 results", true);

        CheckBox_1.setBounds(50, 50, 100, 50); // Adjusted bounds
        CheckBox_2.setBounds(50, 100, 100, 50); // Adjusted bounds

        CheckFrame.add(CheckBox_1);
        CheckFrame.add(CheckBox_2);
        CheckFrame.setSize(400, 400);
        CheckFrame.setLayout(new FlowLayout()); // Set layout manager to FlowLayout
        CheckFrame.setLayout(null);
        CheckFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Test");

    }
}