package me.charlie.qliktech.ui.subwindows;

import me.charlie.qliktech.ui.StartScreen;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: cconroy
 * Date: Jun 13, 2010
 * Time: 3:45:38 PM
 */

abstract public class SubWindow extends JFrame {
    protected StartScreen startScreen;

    public SubWindow(StartScreen ss) {
        this.startScreen = ss;
    }
}
