package de.neuenberger.pokerprofiler.ui;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableCellRenderer;

import de.neuenberger.poker.common.model.Card;
import de.neuenberger.pokerprofiler.ui.tablerenderer.CardArrayRenderer;
import de.neuenberger.pokerprofiler.ui.tablerenderer.FloatRenderer;

public class GamePlayTablePanel extends JPanel {
	JTable jTable=new JTable();
	
	JPanel jpBoardCards=new JPanel();
	JLabel jlPlayersSeeFlop=new JLabel();
	JButton jbLastGame=new JButton(">|"); 
	JButton jbOpen=new JButton("o");
	JButton jbFilter=new JButton("F");
	
	JToolBar jToolBar=new JToolBar();
	
	public GamePlayTablePanel() {
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(jTable),BorderLayout.CENTER);
		this.add(new JScrollPane(jToolBar),BorderLayout.NORTH);
		
		jTable.setDefaultRenderer(String.class, new DefaultTableCellRenderer());
		jTable.setDefaultRenderer(Float.class, new FloatRenderer());
		
		jTable.setDefaultRenderer(Card[].class, new CardArrayRenderer());
		
		jToolBar.add(jlPlayersSeeFlop);
		
		
		jToolBar.add(jpBoardCards);
		
		jToolBar.add(Box.createHorizontalGlue());
		jToolBar.add(jbFilter);
		jToolBar.add(jbOpen);
		jToolBar.add(jbLastGame);
	}

	public JTable getJTable() {
		return jTable;
	}

	public JButton getJbLastGame() {
		return jbLastGame;
	}

	public JLabel getJlPlayersSeeFlop() {
		return jlPlayersSeeFlop;
	}

	public JPanel getJpBoardCards() {
		return jpBoardCards;
	}

	public JButton getJbOpen() {
		return jbOpen;
	}

	public JButton getJbFilter() {
		return jbFilter;
	}
}
