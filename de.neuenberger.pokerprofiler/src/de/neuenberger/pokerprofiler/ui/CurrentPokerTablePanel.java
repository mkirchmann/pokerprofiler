package de.neuenberger.pokerprofiler.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

public class CurrentPokerTablePanel extends JPanel {
	JScrollPane jsp;
	JTable jTable=new JTable();
	JToolBar jToolbar=new JToolBar();
	
	
	JButton jbOpen=new JButton("o");
	JButton jbUpdate=new JButton("u");
	JButton jbCloseAll=new JButton("xX");
	public CurrentPokerTablePanel() {
		
		this.setLayout(new BorderLayout());
		jsp=new JScrollPane(jTable);
		
		this.add(jsp,BorderLayout.CENTER);
		
		this.add(jToolbar,BorderLayout.NORTH);
		
		jToolbar.add(jbOpen);
		jToolbar.add(jbUpdate);
		jToolbar.add(jbCloseAll);
	}

	public JTable getJTable() {
		return jTable;
	}

	public JButton getJbOpen() {
		return jbOpen;
	}

	public JButton getJbUpdate() {
		return jbUpdate;
	}

	public JButton getJbCloseAll() {
		return jbCloseAll;
	}
}
