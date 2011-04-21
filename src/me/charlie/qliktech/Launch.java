package me.charlie.qliktech;

import me.charlie.qliktech.ui.StartScreen;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Jul 11, 2010
 * Time: 12:42:50 PM
 */
public class Launch {

    public static void main(String[] args) {
        SwingUtilities.invokeLater( new Runnable() {
            public void run() {
                StartScreen sc = new StartScreen();
                sc.setVisible(true);
            }

        });
    }
}
