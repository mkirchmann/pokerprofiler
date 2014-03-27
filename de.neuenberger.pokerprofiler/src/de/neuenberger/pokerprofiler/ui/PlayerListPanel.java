package de.neuenberger.pokerprofiler.ui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import de.neuenberger.pokerprofiler.ui.tablerenderer.FloatRenderer;

public class PlayerListPanel extends JPanel {
	
	
	JToolBar jToolBar=new JToolBar();
	JTable jTable=new JTable();
	private JTextField jtfSearch=new JTextField();
	private JButton jbSearch=new JButton("Go!");
	
	public PlayerListPanel() {
		this.setLayout(new BorderLayout());
		
		this.add(new JScrollPane(jTable));
		jTable.setDefaultRenderer(Float.class, new FloatRenderer());
		
		this.add(jToolBar,BorderLayout.NORTH);
		
		jToolBar.add(jtfSearch);
		jToolBar.add(jbSearch);
	}

	public JTable getJTable() {
		return jTable;
	}

	public JButton getJbSearch() {
		return jbSearch;
	}

	public JTextField getJtfSearch() {
		return jtfSearch;
	}
}
