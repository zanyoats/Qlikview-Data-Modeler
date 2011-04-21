package me.charlie.qliktech.ui.subwindows;

import me.charlie.qliktech.representation.tables.BuildJoin;
import me.charlie.qliktech.representation.tables.BuildTable;
import me.charlie.qliktech.representation.tables.SQLTable;
import me.charlie.qliktech.representation.tables.Table;
import me.charlie.qliktech.ui.StartScreen;
import me.charlie.qliktech.util.SpringUtilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
public class NewSqlTableClient extends SubWindow {

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

    // Join Parent and child
    private final DefaultListModel parentModel =
            new DefaultListModel();

    private final JList parentCols =
            new JList(parentModel);

    private final DefaultListModel childModel =
            new DefaultListModel();

    private final JList childCols =
            new JList(childModel);

    // Where clause
    private final JTextArea whereClause =
            new JTextArea(5, 40);

    // Add button Component
    private final JButton addSqlButton =
            new JButton("Add SQL Table");



   // ------------------------------------------------------------------------------------------------------------------

    public NewSqlTableClient(StartScreen _ss) {
        super(_ss);

        setTitle("Add table to the Topic");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPane = getContentPane();
        contentPane.setLayout(new SpringLayout());

        JPanel addPanel = new JPanel(new SpringLayout());
        addPanel.setBorder(new TitledBorder("Add SQL Table"));

        JPanel joinPanel = new JPanel(new SpringLayout());
        joinPanel.setBorder(new TitledBorder("Join Columns"));

        contentPane.add(addPanel);
        contentPane.add(joinPanel);

        int numRows = 0;

        numRows += createRow("Table: ", sqlTableComboBox, addPanel);
        sqlTableComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JComboBox c = (JComboBox)ae.getSource();

                String selection = (String)c.getSelectedItem();

                joinColModel.clear(); payColModel.clear();
                List<String> columns = startScreen.getTableMap().get(selection);
                for (String s : columns) {
                    joinColModel.addElement(s);
                    payColModel.addElement(s);
                }
            }
        });

        numRows += createRow("Alias: ", aliasTextField, addPanel);

        numRows += createRow("Join Column(s): ", new JScrollPane(joinColumns), addPanel);
        joinColumns.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList c = (JList)e.getSource();
                Object[] selections = c.getSelectedValues();

                childModel.clear();
                for (Object o: selections) {
                    childModel.addElement(o);
                }
            }
        });

        numRows += createRow("Payload Column(s): ", new JScrollPane(payloadColumns), addPanel);

        whereClause.setFont(new Font("Serif", Font.ITALIC, 13));
        whereClause.setLineWrap(false);
        createRow("Where: ", new JScrollPane(whereClause), addPanel);

        numRows += createRow("Parent Table Selected: ", new JLabel(startScreen.getCurrentTable().toString()), joinPanel);

        numRows += createRow("Join Parent Column(s): ", new JScrollPane(parentCols), joinPanel);
        for (String c: startScreen.getCurrentTable().getMappingColumns()) {
            parentModel.addElement(c);
        }

        numRows += createRow("Join Child Column(s): ", new JScrollPane(childCols), joinPanel);


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

                if (   whereClause.getText() != null
                    && !whereClause.getText().equals("")) {
                    ((SQLTable) child.getInnerTableType()).setWhereClause(whereClause.getText());
                }

                List<String> parentColList = new ArrayList<String>();
                List<String> childColList = new ArrayList<String>();
                for (Object o: Arrays.asList(parentCols.getSelectedValues())) parentColList.add((String)o);
                for (Object o: Arrays.asList(childCols.getSelectedValues())) childColList.add((String)o);

                new BuildJoin().
                        setJoinOnMe(startScreen.getCurrentTable()).
                        setAddedRelation(child).
                        setParentColumns(parentColList).
                        setChildColumns(childColList).
                        doJoiningAction();

                NewSqlTableClient.this.dispose();
            }
        });
        contentPane.add(addSqlButton);

        //Lay out the buttons in one row and as many columns
        //as necessary, with 6 pixels of padding all around.
        SpringUtilities.makeCompactGrid(contentPane,
                3, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        SpringUtilities.makeCompactGrid(addPanel,
                5, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        SpringUtilities.makeCompactGrid(joinPanel,
                3, 2, //rows, cols
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