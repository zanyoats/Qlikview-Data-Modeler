package me.charlie.qliktech.ui.subwindows;

import me.charlie.qliktech.representation.tables.*;
import me.charlie.qliktech.ui.StartScreen;
import me.charlie.qliktech.util.SpringUtilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Aug 11, 2010
 * Time: 12:23:13 AM
 */
public class NewIntervalTableClient extends SubWindow {

    // UI Components
    private final Container contentPane;

    // Alias text field Component
    private final JTextField aliasTextField =
            new JTextField(10);

    // Join List Component
    private final DefaultListModel discreteModel =
            new DefaultListModel();

    private final JList discreteColumn =
            new JList(discreteModel);

    // Text area for inline table
    private final JTextArea intervalCode =
            new JTextArea(20, 40);

    // Add button Component
    private final JButton addIntervalButton =
            new JButton("Add Interval Table");



   // ------------------------------------------------------------------------------------------------------------------

    public NewIntervalTableClient(StartScreen _ss) {
        super(_ss);

        setTitle("Add table to the Topic");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPane = getContentPane();
        contentPane.setLayout(new SpringLayout());

        JPanel addPanel = new JPanel(new SpringLayout());
        addPanel.setBorder(new TitledBorder("Add Interval Table"));

        JPanel inlineCodePanel = new JPanel(new SpringLayout());
        inlineCodePanel.setBorder(new TitledBorder("Interval Table Code"));

        contentPane.add(addPanel);

        int numRows = 0;

        createRow("Alias: ", aliasTextField, addPanel);
        
        createRow("Parent Table Selected: ",
                  new JLabel(startScreen.getCurrentTable().toString()),
                  addPanel);

        List<String> useColumns = startScreen.getCurrentTable().getUseColumns();
        String[] precedColumns = startScreen.getCurrentTable().precedingLoad().split("\n");
        for (String temp: precedColumns) {
            String[] twoParts = temp.split("AS|as");
            if (twoParts.length >= 2)
                discreteModel.addElement(twoParts[1].substring(0,twoParts[1].length()-1).trim());
        }
        for (String s : useColumns) {
            discreteModel.addElement(s);
        }

        createRow("Select Discrete Field: ", new JScrollPane(discreteColumn), addPanel);

        intervalCode.setFont(new Font("Serif", Font.ITALIC, 13));
        intervalCode.setLineWrap(false);
        intervalCode.setText(
                "This is were your CSV representation of an Inline table goes.\n" +
                        "Teh column above will be compared to the low and high limits.\n" +
                        "Interval table code example:\n\n" +
                        "TASK_START_INTERVAL, TASK_END_INTERVAL, TASK_AGE_INTERVAL\n" +
                        "0, 5, 0-5 DAYS\n" +
                        "6, 9, 6-9 DAYS\n" +
                        "10, 15, 10-15 DAYS\n" +
                        "16, 19, 16-19 DAYS\n" +
                        "20, 1000, 20 OR MORE DAYS"
        );
        inlineCodePanel.add(new JScrollPane(intervalCode));
        contentPane.add(inlineCodePanel);

        addIntervalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String selected = (String) discreteColumn.getSelectedValue();

                String columns = intervalCode.getText().substring(0, intervalCode.getText().indexOf("\n"));
                String[] cols = columns.split(",");
                ArrayList<String> mapCols = new ArrayList<String>();
                mapCols.add(cols[2].trim());
                ArrayList<String> payLoad = new ArrayList<String>();
                for (int i = 0; i < 2; i++) {
                    payLoad.add(cols[i].trim());
                }

                String tableName = startScreen.getCurrentTable().getAlias();
                String aliasName = aliasTextField.getText();

                Table child =
                        new BuildTable(BuildTable.TableTypes.INTERVAL_TABLE).
                                setRelation(tableName).
                                setAlias(aliasName).
                                setMappingColumns(mapCols).
                                setUseColumns(payLoad).
                                build();

                new BuildJoin().
                        setJoinOnMe(startScreen.getCurrentTable()).
                        setAddedRelation(child).
                        setChildColumns(new ArrayList<String>()).
                        doJoiningAction();

                ((IntervalTable) child.getInnerTableType()).setInlineCode(
                        intervalCode.getText());
                ((IntervalTable) child.getInnerTableType()).setSelectedField(
                        tableName + "_" + selected);


                NewIntervalTableClient.this.dispose();
            }
        });
        contentPane.add(addIntervalButton);

        //Lay out the buttons in one row and as many columns
        //as necessary, with 6 pixels of padding all around.
        SpringUtilities.makeCompactGrid(contentPane,
                3, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        SpringUtilities.makeCompactGrid(addPanel,
                3, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        SpringUtilities.makeCompactGrid(inlineCodePanel,
                1, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        // Display the window.
        pack();
        setVisible(true);
    }


    private int createRow(String label, Component c, Container container) {
        JLabel l = new JLabel(label, JLabel.TRAILING);
        container.add(l);
        l.setLabelFor(c);
        container.add(c);
        return 1;
    }
}