package me.charlie.qliktech.events;

import me.charlie.qliktech.TopicManager;
import me.charlie.qliktech.representation.tables.Table;
import me.charlie.qliktech.ui.StartScreen;
import me.charlie.qliktech.ui.menu.TablesPopupMenu;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Jul 11, 2010
 * Time: 12:28:08 PM
 */
public class PopupMenuListener extends MouseAdapter {
    private StartScreen ss;

    public PopupMenuListener(StartScreen ss) {
        this.ss = ss;
    }

    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            TablesPopupMenu.getInstance(ss).getPopup().show(
                    e.getComponent(),
                    e.getX(),
                    e.getY());
            JTree c = (JTree) e.getSource();
            Object t = c.getLastSelectedPathComponent();
            if (t == null) {
                nullWasSelected();
            } else if (t == TopicManager.getInstance().getTopicRootNode()) {
                System.out.println("Yah!");
                nullWasSelected();
            } else if (t instanceof Table) {
                tableWasSelected();
                ss.setCurrentTable((Table)t);
                System.out.println("Selected->"+((Table)t).getAlias());  // TODO: Remove when in beta
            }
        }
    }

    private void nullWasSelected() {
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

        JMenu exportMenu = ss.getExportMenu();

        for (int i = 0; i < exportMenu.getItemCount(); i++) {
            exportMenu.getItem(i).setEnabled(false);
        }
    }

    private void tableWasSelected() {
        for (MenuElement e: TablesPopupMenu.getInstance(ss).getPopup().getSubElements()) {
            if (e == null) {

            } else if (e instanceof JMenuItem) {
                JMenuItem item = (JMenuItem) e;
                if (item.getText() != null) {
                    item.setEnabled(true);
                }
            }
        }

        JMenu exportMenu = ss.getExportMenu();

        for (int i = 0; i < exportMenu.getItemCount(); i++) {
            exportMenu.getItem(i).setEnabled(true);
        }
    }

}
