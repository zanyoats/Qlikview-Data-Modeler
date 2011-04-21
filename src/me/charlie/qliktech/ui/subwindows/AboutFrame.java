package me.charlie.qliktech.ui.subwindows;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JFrame {

    private static final String desc = "Qlikview Data Modeler\n" +
                                       "Author: Charles Conroy\n\n" +
                                       "Put description here .............";
    private JTextArea aboutDesc;


	public AboutFrame() {
		super("About");

        aboutDesc = new JTextArea(desc);

		JPanel p = new JPanel(new BorderLayout());

		p.add(aboutDesc, BorderLayout.NORTH);

		getContentPane().add(p);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
	}
}