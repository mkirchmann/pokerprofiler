package de.neuenberger.pokerprofiler.ui.tablerenderer;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import de.neuenberger.poker.common.model.Card;
import de.neuenberger.poker.common.ui.util.CardLabelSmall;

public class CardArrayRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
		Component comp=super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
		if (arg1==null) {
			return comp;
		}
		Card c[]=(Card[])arg1;
		JPanel jPanel=new JPanel();
		// jPanel.setBackground(Color.WHITE);
		prepareJPanelForCards(jPanel);
		
		
		addCardsToJPanel(jPanel, c);
		
		
		return jPanel;
	}
	
	public static void prepareJPanelForCards(JPanel jPanel) {
		FlowLayout flowLayout=new FlowLayout();
		// flowLayout.setAlignOnBaseline(true);
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setHgap(3);
		jPanel.setLayout(flowLayout);
	}
	
	public static void addCardsToJPanel(JPanel jPanel, Card c[]) {
		for (int i=0; i<c.length; i++) {
			CardLabelSmall cls=new CardLabelSmall();
			jPanel.add(cls);
			cls.setCard(c[i]);
			cls.setOpaque(false);
		}
	}

}
