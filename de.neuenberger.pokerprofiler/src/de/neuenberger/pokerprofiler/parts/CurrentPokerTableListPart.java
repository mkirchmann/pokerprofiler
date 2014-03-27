package de.neuenberger.pokerprofiler.parts;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.neuenberger.pokercalc.parts.IController;
import de.neuenberger.pokerprofiler.model.GameDescription;
import de.neuenberger.pokerprofiler.model.PokerTableDescription;
import de.neuenberger.pokerprofiler.model.PokerTableDescriptionChangeList;
import de.neuenberger.pokerprofiler.parts.util.TableHUDManager;
import de.neuenberger.pokerprofiler.ui.CurrentPokerTablePanel;

public class CurrentPokerTableListPart implements IController {

	CurrentPokerTablePanel currentPokerTablePanel=new CurrentPokerTablePanel();
	PokerTableDescriptionChangeList pokerTableDescriptionChangeList=new PokerTableDescriptionChangeList();
	
	
	
	public CurrentPokerTableListPart() {
		
		currentPokerTablePanel.getJTable().setModel(pokerTableDescriptionChangeList);
		
		
		currentPokerTablePanel.getJbOpen().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				openSelection();
			}
			
		});
		
		currentPokerTablePanel.getJbUpdate().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				updateAll();
			}
			
		});
		
		currentPokerTablePanel.getJbCloseAll().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				TableHUDManager.getInstance().closeAll();
				pokerTableDescriptionChangeList.removeAll();
			}
			
		});
		
	}
	
	protected void openSelection() {
		
		int idx=currentPokerTablePanel.getJTable().getSelectedRow();
		if (idx==-1) {
			return;
		}
		
		PokerTableDescription[] tableDescriptions = pokerTableDescriptionChangeList.getTablesAsArray();
		if (tableDescriptions.length>idx) {
			PokerTableDescription description = tableDescriptions[idx];
			TableHUDManager.getInstance().open(description);
		}
	}
	
	protected void updateAll() {
		
		PokerTableDescription[] tableDescriptions = pokerTableDescriptionChangeList.getTablesAsArray();
		for (int i=0; i<tableDescriptions.length; i++) {
			PokerTableDescription description = tableDescriptions[i];
			if (TableHUDManager.getInstance().isOpened(description)) {
				TableHUDManager.getInstance().update(description);
			}
		}
	}

	public Component getComponent() {
		return currentPokerTablePanel;
	}

	public void saveComponents() {
		// TODO Auto-generated method stub

	}

	public JTable getJTable() {
		return currentPokerTablePanel.getJTable();
	}

	public PokerTableDescriptionChangeList getPokerTableDescriptionChangeList() {
		return pokerTableDescriptionChangeList;
	}

	public CurrentPokerTablePanel getCurrentPokerTablePanel() {
		return currentPokerTablePanel;
	}

}
