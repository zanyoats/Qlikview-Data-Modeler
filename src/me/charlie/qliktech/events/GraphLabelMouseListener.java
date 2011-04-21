package me.charlie.qliktech.events;

import me.charlie.qliktech.representation.visitor.TransformToDotV;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: May 14, 2010
 * Time: 8:48:22 PM
 */

public class GraphLabelMouseListener implements MouseListener {

    public void mousePressed(MouseEvent e) {
        new TransformToDotV();
        System.out.println("Mouse pressed; # of clicks: "
                + e.getClickCount());
        JLabel label = (JLabel) e.getSource();
        ImageIcon imageIcon = (ImageIcon)label.getIcon();
        imageIcon.getImage().flush();
        label.revalidate();
        label.repaint();
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
}