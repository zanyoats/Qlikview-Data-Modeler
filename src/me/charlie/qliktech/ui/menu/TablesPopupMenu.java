package me.charlie.qliktech.ui.menu;

import me.charlie.qliktech.representation.tables.Table;
import me.charlie.qliktech.representation.tables.TableTree;
import me.charlie.qliktech.representation.types.RelType;
import me.charlie.qliktech.representation.types.RelTypeFactory;
import me.charlie.qliktech.ui.StartScreen;
import me.charlie.qliktech.ui.subwindows.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Jul 11, 2010
 * Time: 1:00:47 PM
 */
public class TablesPopupMenu {

    private static TablesPopupMenu uniqueInstance;
    private final StartScreen ss;
    private JPopupMenu popup;
    private JMenuItem addPrecedLoad;
    private JMenu addRelType;

    private TablesPopupMenu(final StartScreen ss) {
        this.ss = ss;
        popup = new JPopupMenu();

        JMenuItem rootItem = new JMenuItem("New Root Table", new ImageIcon("images/add.png"));
        JMenuItem addJoinItem = new JMenuItem("Sql Table", new ImageIcon("images/add.png"));
        JMenuItem addResidentItem = new JMenuItem("Resident Table", new ImageIcon("images/add.png"));
        JMenuItem addInlineItem = new JMenuItem("Inline Table", new ImageIcon("images/add.png"));
        JMenuItem addIntervalItem = new JMenuItem("Interval Table", new ImageIcon("images/add.png"));
        JMenuItem addCodeItem = new JMenuItem("Custom Table", new ImageIcon("images/add.png"));
        addPrecedLoad = new JMenuItem("Add Preceding Load", new ImageIcon("images/add.png"));
        addRelType = new JMenu("Set Relation Type");
        JMenuItem removeItem = new JMenuItem("Remove Selected Table", new ImageIcon("images/remove.png"));


        rootItem.setEnabled(true);
        addJoinItem.setEnabled(true);
        addResidentItem.setEnabled(true);
        addInlineItem.setEnabled(true);
        addIntervalItem.setEnabled(true);
        addCodeItem.setEnabled(true);
        addPrecedLoad.setEnabled(true);
        addRelType.setEnabled(true);
        removeItem.setEnabled(true);



        rootItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new NewRootTableClient(ss);
                    }
                });
            }
        });
        addJoinItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new NewSqlTableClient(ss);
                    }
                });
            }
        });
        addResidentItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new NewResidentTableClient(ss);
                    }
                });
            }
        });
        addInlineItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new NewInlineTableClient(ss);
                    }
                });
            }
        });
        addIntervalItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new NewIntervalTableClient(ss);
                    }
                });
            }
        });
        addCodeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new NewCustomTableClient(ss);
                    }
                });
            }
        });
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                TableTree target = (TableTree) ss.getCurrentTable();
                if (target != null) {
                    //TableTree root = (TableTree) ss.getListModel().getElementAt(0);
                    //root.accept(new RemoveTableV(target));
                }
            }
        });

        popup.add(rootItem);
        popup.addSeparator();
        popup.add(addJoinItem);
        popup.add(addResidentItem);
        popup.add(addInlineItem);
        popup.add(addIntervalItem);
        popup.add(addCodeItem);
        popup.addSeparator();
        popup.add(addPrecedLoad);
        popup.addSeparator();
        popup.add(removeItem);
        popup.addSeparator();
        popup.add(addRelType);

        addPrecedLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Table current = ss.getCurrentTable();
                if (current != null) {
                    javax.swing.SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new NewPrecedLoadClient(ss);
                        }
                    });
                }
            }
        });

        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem rmbi;
        rmbi = new JRadioButtonMenuItem("Outer Keep");
        rmbi.addActionListener(setRelTypeActionListener(RelTypeFactory.createType(RelTypeFactory.RelTypes.OUTER_KEEP)));
        rmbi.setSelected(true);
        addRelType.add(rmbi);
        group.add(rmbi);

        rmbi = new JRadioButtonMenuItem("Outer Join");
        rmbi.addActionListener(setRelTypeActionListener(RelTypeFactory.createType(RelTypeFactory.RelTypes.OUTER_JOIN)));
        addRelType.add(rmbi);
        group.add(rmbi);

        rmbi = new JRadioButtonMenuItem("Inner Keep");
        rmbi.addActionListener(setRelTypeActionListener(RelTypeFactory.createType(RelTypeFactory.RelTypes.INNER_KEEP)));
        addRelType.add(rmbi);
        group.add(rmbi);

        rmbi = new JRadioButtonMenuItem("Inner Join");
        rmbi.addActionListener(setRelTypeActionListener(RelTypeFactory.createType(RelTypeFactory.RelTypes.INNER_JOIN)));
        addRelType.add(rmbi);
        group.add(rmbi);

        rmbi = new JRadioButtonMenuItem("Left Keep");
        rmbi.addActionListener(setRelTypeActionListener(RelTypeFactory.createType(RelTypeFactory.RelTypes.LEFT_KEEP)));
        addRelType.add(rmbi);
        group.add(rmbi);

        rmbi = new JRadioButtonMenuItem("Left Join");
        rmbi.addActionListener(setRelTypeActionListener(RelTypeFactory.createType(RelTypeFactory.RelTypes.LEFT_JOIN)));
        addRelType.add(rmbi);
        group.add(rmbi);

        rmbi = new JRadioButtonMenuItem("Right Keep");
        rmbi.addActionListener(setRelTypeActionListener(RelTypeFactory.createType(RelTypeFactory.RelTypes.RIGHT_KEEP)));
        addRelType.add(rmbi);
        group.add(rmbi);

        rmbi = new JRadioButtonMenuItem("Right Join");
        rmbi.addActionListener(setRelTypeActionListener(RelTypeFactory.createType(RelTypeFactory.RelTypes.RIGHT_JOIN)));
        addRelType.add(rmbi);
        group.add(rmbi);
    }

    private ActionListener setRelTypeActionListener(final RelType t) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Table current = ss.getCurrentTable();
                if (current != null) {
                    if (current.getMyParent() == null) {
                        addRelType.setEnabled(false);
                    } else {
                        addRelType.setEnabled(true);
                        current.setRelType(t);
                    }
                }
            }
        };
    }

    public static TablesPopupMenu getInstance(StartScreen ss) {
        if (uniqueInstance == null) {
            uniqueInstance = new TablesPopupMenu(ss);
        }
        return uniqueInstance;
    }

    public JPopupMenu getPopup() {
        return popup;
    }
}
