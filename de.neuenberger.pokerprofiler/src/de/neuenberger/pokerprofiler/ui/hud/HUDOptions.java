package de.neuenberger.pokerprofiler.ui.hud;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;
import javax.swing.border.LineBorder;

public class HUDOptions extends JWindow {

	JLabel jlSetup=new JLabel("s");
	JLabel jlClose=new JLabel("x");
	JLabel jlDetailled=new JLabel("d");
	
	public HUDOptions() {
		
		GridLayout gridLayout=new GridLayout(1,3);
		gridLayout.setRows(1);
		gridLayout.setColumns(3);
		gridLayout.setHgap(0);
		gridLayout.setVgap(0);
		this.getContentPane().setLayout(gridLayout);
		
		this.getContentPane().add(jlDetailled);
		this.getContentPane().add(jlSetup);
		this.getContentPane().add(jlClose);
		
		this.setAlwaysOnTop(true);
		jlClose.setForeground(Color.RED);
		jlClose.setBackground(Color.BLACK);
		jlClose.setOpaque(true);
		jlClose.setBorder(new LineBorder(Color.GRAY));
		
		jlSetup.setForeground(Color.BLUE);
		jlSetup.setBackground(Color.BLACK);
		jlSetup.setOpaque(true);
		jlSetup.setBorder(new LineBorder(Color.GRAY));
		
		jlDetailled.setForeground(Color.YELLOW);
		jlDetailled.setBackground(Color.BLACK);
		jlDetailled.setOpaque(true);
		jlDetailled.setBorder(new LineBorder(Color.GRAY));
		
		this.setBackground(Color.BLACK);
		
	}

	public JLabel getJlClose() {
		return jlClose;
	}

	public JLabel getJlDetailled() {
		return jlDetailled;
	}

	public JLabel getJlSetup() {
		return jlSetup;
	}

}
