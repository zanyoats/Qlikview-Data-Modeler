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
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Aug 11, 2010
 * Time: 12:23:13 AM
 */
public class NewCustomTableClient extends SubWindow {

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
    private final JTextArea customCode =
            new JTextArea(10, 40);
    private final JTextArea addMapColumsArea =
            new JTextArea(5, 40);
    private final JTextArea addUseColumsArea =
            new JTextArea(5, 40);


    // Add button Component
    private final JButton addCustomButton =
            new JButton("Add Custom Table");



   // ------------------------------------------------------------------------------------------------------------------

    public NewCustomTableClient(StartScreen _ss) {
        super(_ss);

        setTitle("Add table to the Topic");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPane = getContentPane();
        contentPane.setLayout(new SpringLayout());

        JPanel addPanel = new JPanel(new SpringLayout());
        addPanel.setBorder(new TitledBorder("Add Custom Table"));

        JPanel inlineCodePanel = new JPanel(new SpringLayout());
        inlineCodePanel.setBorder(new TitledBorder("Custom Table Code"));

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

        customCode.setFont(new Font("Serif", Font.ITALIC, 13));
        customCode.setLineWrap(false);
        customCode.setText(
                "Your custom LOAD statement goes here.\n" +
                        "Include the column(s) selected above in the correct order.\n"  +
                        "For instance say you selected just column 'ID' above, than below is a correct example:\n\n"+
                "LOAD\n" +
                        "ID,  // This is the joining column to the parent\n" +
                        "AGE;\n" +
                        "SELECT\n" +
                        "ID,\n" +
                        "trunc(sysdate) - trunc(created_date) as AGE\n" +
                        "FROM PV_TASKS;"
        );
        inlineCodePanel.add(new JScrollPane(customCode));


        addMapColumsArea.setFont(new Font("Serif", Font.ITALIC, 13));
        addMapColumsArea.setLineWrap(false);
        addMapColumsArea.setText(
                "Add all mapping columns..."
        );
        inlineCodePanel.add(new JScrollPane(addMapColumsArea));

        addUseColumsArea.setFont(new Font("Serif", Font.ITALIC, 13));
        addUseColumsArea.setLineWrap(false);
        addUseColumsArea.setText(
                "Add all payload columns..."
        );
        inlineCodePanel.add(new JScrollPane(addUseColumsArea));

        contentPane.add(inlineCodePanel);

        addCustomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {



                String tableName = startScreen.getCurrentTable().getAlias();
                String aliasName = aliasTextField.getText();
                List<String> joinCols = new ArrayList<String>();
                for (Object o: Arrays.asList(joinColumns.getSelectedValues())) joinCols.add((String)o);

                List<String> mapCols = new ArrayList<String>();
                mapCols.addAll(Arrays.asList(addMapColumsArea.getText().split("\n")));
                List<String> payLoad = new ArrayList<String>();
                payLoad.addAll(Arrays.asList(addUseColumsArea.getText().split("\n")));


                Table child =
                        new BuildTable(BuildTable.TableTypes.CUSTOM_TABLE).
                                setRelation(tableName).
                                setAlias(aliasName).
                                setMappingColumns(mapCols).
                                setUseColumns(payLoad).
                                build();

                ((CustomTable) child.getInnerTableType()).setInlineCode(customCode.getText());

                new BuildJoin().
                        setJoinOnMe(startScreen.getCurrentTable()).
                        setAddedRelation(child).
                        setParentColumns(joinCols).
                        setChildColumns(mapCols).
                        doJoiningAction();

                NewCustomTableClient.this.dispose();
            }
        });
        contentPane.add(addCustomButton);

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
                3, 1, //rows, cols
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