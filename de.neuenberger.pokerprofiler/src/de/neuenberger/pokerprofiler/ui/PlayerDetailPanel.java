package de.neuenberger.pokerprofiler.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.neuenberger.poker.common.model.Card;
import de.neuenberger.pokerprofiler.ui.tablerenderer.CardArrayRenderer;

public class PlayerDetailPanel extends JPanel {
	
	JTable jTable=new JTable();
	JPanel jpBottomPanel=new JPanel();
	
	JButton jbClear=new JButton("c");
	JButton jbFilter=new JButton("F");
	JLabel jlStatus=new JLabel();
	
	public PlayerDetailPanel() {
		this.setLayout(new BorderLayout());
		
		
		this.add(new JScrollPane(jTable));
		this.add(jpBottomPanel,BorderLayout.SOUTH);
		jTable.setDefaultRenderer(Card[].class, new CardArrayRenderer());
		
		FlowLayout flowLayout=new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		jpBottomPanel.setLayout(new BorderLayout());
		
		JPanel jpInnerPanel=new JPanel();
		jpInnerPanel.setLayout(flowLayout);
		
		jpInnerPanel.add(jbFilter);
		jpInnerPanel.add(jbClear);
		jpBottomPanel.add(jpInnerPanel,BorderLayout.WEST);
		jpBottomPanel.add(jlStatus,BorderLayout.CENTER);
		jlStatus.setBackground(Color.YELLOW);
	}

	public JTable getJTable() {
		return jTable;
	}

	public JButton getJbClear() {
		return jbClear;
	}

	public JLabel getJlStatus() {
		return jlStatus;
	}

	public JButton getJbFilter() {
		return jbFilter;
	}
}
