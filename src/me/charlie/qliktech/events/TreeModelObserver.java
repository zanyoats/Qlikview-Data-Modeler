package me.charlie.qliktech.events;

import me.charlie.qliktech.TopicManager;
import me.charlie.qliktech.ui.StartScreen;
import me.charlie.qliktech.ui.menu.TablesPopupMenu;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Aug 12, 2010
 * Time: 1:23:40 PM
 */
public class TreeModelObserver implements TreeModelListener {

    private final StartScreen ss;

    public TreeModelObserver(StartScreen _ss) {
        ss = _ss;
    }

    public void treeNodesChanged(TreeModelEvent e) {
        System.out.println("TREE MODEL [treeNodesChanged]");
        updateTree();
    }
    public void treeNodesInserted(TreeModelEvent e) {
        System.out.println("TREE MODEL [treeNodesInserted]");
        updateTree();
    }
    public void treeNodesRemoved(TreeModelEvent e) {
        System.out.println("TREE MODEL [treeNodesRemoved]");
        updateTree();
    }
    public void treeStructureChanged(TreeModelEvent e) {
        System.out.println("TREE MODEL [treeStructureChanged]");
        updateTree();
    }
    private void updateTree() {

        if (TopicManager.getInstance().getTopicRootNode().getChildCount() <= 0) {

            for (MenuElement e: TablesPopupMenu.getInstance(ss).getPopup().getSubElements()) {
                if (e == null) {

                } else if (e instanceof JMenuItem) {
                    JMenuItem item = (JMenuItem) e;
                    if (item.getText().equals("New Root Table")) {
                        item.setEnabled(true);
                    } else if (item.getText() != null) {
                        item.setEnabled(false);
                    }
                }
            }

        } else {



            for (MenuElement e: TablesPopupMenu.getInstance(ss).getPopup().getSubElements()) {
                if (e == null) {

                } else if (e instanceof JMenuItem) {
                    JMenuItem item = (JMenuItem) e;
                    if (item.getText() != null) {
                        item.setEnabled(true);
                    }
                }
            }

        }
    }
}
