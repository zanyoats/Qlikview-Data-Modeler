package me.charlie.qliktech.ui.subwindows;

import me.charlie.qliktech.representation.tables.BuildJoin;
import me.charlie.qliktech.representation.tables.BuildTable;
import me.charlie.qliktech.representation.tables.InlineTable;
import me.charlie.qliktech.representation.tables.Table;
import me.charlie.qliktech.ui.StartScreen;
import me.charlie.qliktech.util.SpringUtilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Aug 11, 2010
 * Time: 12:23:13 AM
 */
public class NewInlineTableClient extends SubWindow {

    // UI Components
    private final Container contentPane;

    // Alias text field Component
    private final JTextField aliasTextField =
            new JTextField(10);

    // Join List Component
    private final DefaultListModel joinColModel =
            new DefaultListModel();

    private final JList joinColumns =
            new JList(joinColModel);

    // Text area for inline table
    private final JTextArea inlineCode =
            new JTextArea(20, 40);

    // Add button Component
    private final JButton addInlineButton =
            new JButton("Add Inline Table");



   // ------------------------------------------------------------------------------------------------------------------

    public NewInlineTableClient(StartScreen _ss) {
        super(_ss);

        setTitle("Add table to the Topic");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPane = getContentPane();
        contentPane.setLayout(new SpringLayout());

        JPanel addPanel = new JPanel(new SpringLayout());
        addPanel.setBorder(new TitledBorder("Add Inline Table"));

        JPanel inlineCodePanel = new JPanel(new SpringLayout());
        inlineCodePanel.setBorder(new TitledBorder("Inline Table Code"));

        contentPane.add(addPanel);

        int numRows = 0;

        createRow("Alias: ", aliasTextField, addPanel);
        
        createRow("Parent Table Selected: ",
                  new JLabel(startScreen.getCurrentTable().toString()),
                  addPanel);

        List<String> joinCols = startScreen.getCurrentTable().getMappingColumns();
        for (String s : joinCols) {
            joinColModel.addElement(s);
        }

        createRow("Join Parent Column(s): ", new JScrollPane(joinColumns), addPanel);

        inlineCode.setFont(new Font("Serif", Font.ITALIC, 13));
        inlineCode.setLineWrap(false);
        inlineCode.setText(
                "This is were your CSV representation of an Inline table goes.\n" +
                        "Include the column(s) selected above in the correct order.\n" +
                        "Inline table code example:\n" +
                        "PERSON_ID, NICKNAME\n" +
                        "1, Joey\n" +
                        "2, Davey\n" +
                        "3, Charlie\n"
        );
        inlineCodePanel.add(new JScrollPane(inlineCode));
        contentPane.add(inlineCodePanel);

        addInlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String columns = inlineCode.getText().substring(0,inlineCode.getText().indexOf("\n"));
                String[] cols = columns.split(",");
                ArrayList<String> mapCols = new ArrayList<String>();
                int i;
                for (i = 0; i < joinColumns.getSelectedValues().length; i++) {
                    mapCols.add(cols[i].trim());
                }
                ArrayList<String> payLoad = new ArrayList<String>();
                for (; i < cols.length; i++) {
                    payLoad.add(cols[i].trim());
                }

                String tableName = startScreen.getCurrentTable().getAlias();
                String aliasName = aliasTextField.getText();
                List<String> joinCols = new ArrayList<String>();
                for (Object o: Arrays.asList(joinColumns.getSelectedValues())) joinCols.add((String)o);

                Table child =
                        new BuildTable(BuildTable.TableTypes.INLINE_TABLE).
                                setRelation(tableName).
                                setAlias(aliasName).
                                setMappingColumns(mapCols).
                                setUseColumns(payLoad).
                                build();

                ((InlineTable) child.getInnerTableType()).setInlineCode(inlineCode.getText());

                new BuildJoin().
                        setJoinOnMe(startScreen.getCurrentTable()).
                        setAddedRelation(child).
                        setParentColumns(joinCols).
                        setChildColumns(joinCols).
                        doJoiningAction();

                NewInlineTableClient.this.dispose();
            }
        });
        contentPane.add(addInlineButton);

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