package me.charlie.qliktech.ui.subwindows;

import me.charlie.qliktech.representation.tables.*;
import me.charlie.qliktech.ui.StartScreen;
import me.charlie.qliktech.util.SpringUtilities;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Aug 11, 2010
 * Time: 12:23:13 AM
 */
public class NewPrecedLoadClient extends SubWindow {

    // UI Components
    private final Container contentPane;


    // Text area for inline table
    private final JTextArea currentLoad =
            new JTextArea(10, 40);

    private final JTextArea columnsToAdd =
            new JTextArea(10, 40);

    // Add button Component
    private final JButton addPrecedLoad =
            new JButton("Add Preceding Load");



   // ------------------------------------------------------------------------------------------------------------------

    public NewPrecedLoadClient(StartScreen _ss) {
        super(_ss);

        setTitle("Add table to the Topic");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPane = getContentPane();
        contentPane.setLayout(new SpringLayout());

        JPanel currLoadStmt = new JPanel(new SpringLayout());
        currLoadStmt.setBorder(new TitledBorder("Current Load Statement"));

        JPanel addCols = new JPanel(new SpringLayout());
        addCols.setBorder(new TitledBorder("Add Columns"));


        currentLoad.setFont(new Font("Serif", Font.ITALIC, 13));
        currentLoad.setLineWrap(false);
        currentLoad.setText(
            startScreen.getCurrentTable().precedingLoad()
        );
        currentLoad.setEditable(false);
        currLoadStmt.add(new JScrollPane(currentLoad));
        contentPane.add(currLoadStmt);

        columnsToAdd.setFont(new Font("Serif", Font.ITALIC, 13));
        columnsToAdd.setLineWrap(false);
        columnsToAdd.setText(
                "To import all previous columns use \"*\""
        );
        addCols.add(new JScrollPane(columnsToAdd));
        contentPane.add(addCols);


        addPrecedLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                BuildPrecedLoad bpl = new BuildPrecedLoad(startScreen.getCurrentTable().getInnerTableType());
                startScreen.getCurrentTable().setInnerTableType(bpl.getDecoratedTable());

                PrecedLoadDecorator plc =
                        (PrecedLoadDecorator) startScreen.getCurrentTable().getInnerTableType();

                String[] columns = columnsToAdd.getText().split("\n");
                for (String col: columns) {
                    plc.addPrecedingColumn(col.trim());
                }

                NewPrecedLoadClient.this.dispose();
            }
        });
        contentPane.add(addPrecedLoad);

        //Lay out the buttons in one row and as many columns
        //as necessary, with 6 pixels of padding all around.
        SpringUtilities.makeCompactGrid(contentPane,
                3, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        SpringUtilities.makeCompactGrid(currLoadStmt,
                1, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        SpringUtilities.makeCompactGrid(addCols,
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