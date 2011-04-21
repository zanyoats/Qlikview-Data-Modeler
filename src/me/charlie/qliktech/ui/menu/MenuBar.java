package me.charlie.qliktech.ui.menu;

import com.thoughtworks.xstream.XStream;
import me.charlie.qliktech.TopicManager;
import me.charlie.qliktech.representation.tables.Table;
import me.charlie.qliktech.representation.tables.TableTree;
import me.charlie.qliktech.representation.visitor.TransformToQlikTechCodeV;
import me.charlie.qliktech.ui.subwindows.AboutFrame;
import me.charlie.qliktech.ui.StartScreen;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MenuBar {

    private StartScreen ss;

    private JMenu insightMenu;
    private JMenu analyzeMenu;
    private JMenu exportMenu;

    public MenuBar(StartScreen ss) {
        this.ss = ss;
    }

    public JMenuBar createMenu() {
        JMenuBar menuBar = new JMenuBar();

        /** Menus
         * Insight Data Modeler, Add Tables, Analyze & Inspection, Export
         */
        insightMenu = new JMenu("File");
        analyzeMenu = new JMenu("Refactor");
        exportMenu = new JMenu("Export");

        menuBar.add(insightMenu);
        menuBar.add(analyzeMenu);
        menuBar.add(exportMenu);

        menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));




        /** Adding components to
         * File menu.
         */
        JMenuItem saveItem = new JMenuItem("Save Model");
        JMenuItem importItem = new JMenuItem("Import Model");
        JMenuItem aboutHelpItem = new JMenuItem("About");
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem closeItem = new JMenuItem("Close");

        closeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        aboutHelpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AboutFrame af = new AboutFrame();
                        af.setVisible(true);
                    }
                });
            }
        });
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String filename = (String) TopicManager.getInstance().getTopicRootNode().getUserObject();

                List<Object> nodes = new ArrayList<Object>();
                nodes.add(TopicManager.getInstance().getTreeModel());
                nodes.add(TopicManager.getInstance().getTopicRootNode());

                Enumeration allChildNodes = TopicManager.getInstance().getTopicRootNode().breadthFirstEnumeration();
                for (; allChildNodes.hasMoreElements() ;) {
                    nodes.add(allChildNodes.nextElement());
                }

                try {
                    PrintWriter out = new PrintWriter( new FileOutputStream(new File(filename + ".xml")) );
                    XStream xstream = new XStream();
                    xstream.toXML(nodes, out);
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        importItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String filename = JOptionPane.showInputDialog("Import work as... File ends in 'xml'");

                if (filename == null || filename.equals("")) return;

                try {
                    FileReader in = new FileReader( new File(filename) );
                    XStream xstream = new XStream();
                    List<Object> nodes = (List<Object>) xstream.fromXML(in);
                    TopicManager.getInstance(nodes.get(0), nodes.get(1));
                    TopicManager.getInstance().getTreeModel().reload();
                    ss.getTree().setModel(TopicManager.getInstance().getTreeModel());
                    in.close();

                } catch (FileNotFoundException e) {
                    JOptionPane.showMessageDialog(ss,
                                                  filename + ": was not found. Please enter another file.",
                                                 "File not found",
                                                 JOptionPane.ERROR_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        insightMenu.add(saveItem);
        insightMenu.add(importItem);
        insightMenu.add(aboutHelpItem);
        insightMenu.add(helpItem);
        insightMenu.add(closeItem);



        /** Adding components to
         * Export menu.
         */
        JMenuItem applyMapItem = new JMenuItem("Apply Map to Topic");
        JMenuItem circularRefItem = new JMenuItem("(No circle references found...)");

        applyMapItem.setEnabled(false);
        circularRefItem.setEnabled(false);

        analyzeMenu.add(applyMapItem);
        analyzeMenu.add(circularRefItem);




        /** Adding components to
         * Export menu.
         */
        JMenuItem exportCodeItem = new JMenuItem("Insight Code", new ImageIcon("images/export.png"));
        JMenuItem exportArtifactsItem = new JMenuItem("Eng. Document Artifacts", new ImageIcon("images/export.png"));

        exportCodeItem.setEnabled(true);
        exportArtifactsItem.setEnabled(false);

        exportCodeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new TransformToQlikTechCodeV();
            }
        });

        exportMenu.add(exportCodeItem);
        exportMenu.add(exportArtifactsItem);


        return menuBar;
    }

    public JMenu getAnalyzeMenu() {
        return analyzeMenu;
    }

    public JMenu getExportMenu() {
        return exportMenu;
    }

    public JMenu getInsightMenu() {
        return insightMenu;
    }
}
