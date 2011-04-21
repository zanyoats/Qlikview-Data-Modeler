package me.charlie.qliktech.ui.subwindows;

import me.charlie.qliktech.TopicManager;
import me.charlie.qliktech.representation.tables.BuildTable;
import me.charlie.qliktech.representation.tables.Table;
import me.charlie.qliktech.ui.StartScreen;
import me.charlie.qliktech.util.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Aug 11, 2010
 * Time: 12:23:13 AM
 */
public class NewRootTableClient extends SubWindow {

    // UI Components
    private final Container contentPane;

    // Table combo box Component
    private final JComboBox sqlTableComboBox =
            new JComboBox(startScreen.getTableMap().keySet().toArray());

    // Alias text field Component
    private final JTextField aliasTextField =
            new JTextField(10);

    // Join List Component
    private final DefaultListModel joinColModel =
            new DefaultListModel();

    private final JList joinColumns =
            new JList(joinColModel);

    // Payload List Component
    private final DefaultListModel payColModel =
            new DefaultListModel();

    private final JList payloadColumns =
            new JList(payColModel);

    // Add button Component
    private final JButton addSqlButton =
            new JButton("Add SQL Table");


   // ------------------------------------------------------------------------------------------------------------------

    public NewRootTableClient(StartScreen _ss) {
        super(_ss);

        setTitle("Add New Root Table");
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPane = getContentPane();
        contentPane.setLayout(new SpringLayout());

        int numRows = 0;

        numRows += createRow("Table: ", sqlTableComboBox);
        sqlTableComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JComboBox c = (JComboBox)ae.getSource();
                
                String selection = (String)c.getSelectedItem();

                joinColModel.clear(); payColModel.clear();
                java.util.List<String> columns = startScreen.getTableMap().get(selection);
                for (String s : columns) {
                    joinColModel.addElement(s);
                    payColModel.addElement(s);
                }
            }
        });

        numRows += createRow("Alias: ", aliasTextField);

        numRows += createRow("Join Column(s): ", new JScrollPane(joinColumns));

        numRows += createRow("Payload Column(s): ", new JScrollPane(payloadColumns));

        numRows += createRow(" ", addSqlButton);
        addSqlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String tableName = (String) sqlTableComboBox.getSelectedItem();
                String aliasName = aliasTextField.getText();
                List<String> joinCols = new ArrayList<String>();
                List<String> payCols = new ArrayList<String>();
                for (Object o: Arrays.asList(joinColumns.getSelectedValues())) joinCols.add((String)o);
                for (Object o: Arrays.asList(payloadColumns.getSelectedValues())) payCols.add((String)o);

                Table child =
                        new BuildTable(BuildTable.TableTypes.SQL_TABLE).
                            setRelation(tableName).
                            setAlias(aliasName).
                            setMappingColumns(joinCols).
                            setUseColumns(payCols).
                            build();

                TopicManager.getInstance().getTreeModel().insertNodeInto(
                        child,
                        TopicManager.getInstance().getTopicRootNode(),
                        TopicManager.getInstance().getTopicRootNode().getChildCount()
                );

                NewRootTableClient.this.dispose();
            }
        });

        
        //Lay out the buttons in one row and as many columns
        //as necessary, with 6 pixels of padding all around.
        SpringUtilities.makeCompactGrid(contentPane,
                                        numRows, 2, //rows, cols
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad

        // Display the window.
        pack();
        setVisible(true);
    }


    private int createRow(String label, Component c) {
        JLabel l = new JLabel(label, JLabel.TRAILING);
        contentPane.add(l);
        l.setLabelFor(c);
        contentPane.add(c);
        return 1;
    }
}
