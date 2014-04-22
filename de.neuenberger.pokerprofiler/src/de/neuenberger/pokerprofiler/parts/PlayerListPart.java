package de.neuenberger.pokerprofiler.parts;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import de.neuenberger.poker.common.parts.IController;
import de.neuenberger.pokerprofiler.logic.parser.ParserPool;
import de.neuenberger.pokerprofiler.model.PlayerDescription;
import de.neuenberger.pokerprofiler.model.PlayerDescriptionList;
import de.neuenberger.pokerprofiler.model.contentprovider.DetailedPlayerListTableModel;
import de.neuenberger.pokerprofiler.ui.PlayerListPanel;

public class PlayerListPart implements IController {

	PlayerListPanel playerListPanel=new PlayerListPanel();
	DetailedPlayerListTableModel detailedPlayerListTableModel=new DetailedPlayerListTableModel(null); 
	ParserPool pp=ParserPool.getInstance();
	
	PlayerDescriptionList playerdescriptionList= pp.getSelectedHistoryAnalyzer().getPlayerDescriptionList();
	
	
	public PlayerListPart() {
		this.playerListPanel.getJTable().setModel(detailedPlayerListTableModel);
		
		detailedPlayerListTableModel.setPlayerDescriptionList(pp.getInstance().getSelectedHistoryAnalyzer().getPlayerDescriptionList());
		
		
		playerListPanel.getJbSearch().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				execute_search();
			}
		});
		
		playerListPanel.getJtfSearch().addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyChar()==(char)10 || arg0.getKeyChar()==(char)13) {
					execute_search();
				}
			}

			public void keyReleased(KeyEvent arg0) {
				//
			}

			public void keyTyped(KeyEvent arg0) {
				//
			}
		});
	}
	
	protected void execute_search() {
		String search=playerListPanel.getJtfSearch().getText().toLowerCase();
		int r=playerListPanel.getJTable().getSelectedRow();
		if (r==-1) {
			r=0;
		} else if (!getLowerCaseName(playerdescriptionList.getPlayerDescription(r)).contains(search)) {
			r=0;
		} else {
			r++;
		}
		
		for (int i=r; i<playerdescriptionList.getSize(); i++) {
			if (getLowerCaseName(playerdescriptionList.getPlayerDescription(i)).contains(search)) {
				int rh=playerListPanel.getJTable().getRowHeight();
				int s=i*rh;
				Rectangle rect=new Rectangle(1,s,10,s+rh);
				playerListPanel.getJTable().scrollRectToVisible(rect);
				playerListPanel.getJTable().getSelectionModel().setSelectionInterval(i,i);
				return;
			}
		}
		JOptionPane.showMessageDialog(playerListPanel, "Player not found");
		
		
	}
	
	protected String getLowerCaseName(PlayerDescription pd) {
		return pd.getName().toLowerCase();
	}

	public Component getComponent() {
		return playerListPanel;
	}

	public void saveComponents() {
		// TODO Auto-generated method stub

	}

	public PlayerDescriptionList getPlayerdescriptionList() {
		return playerdescriptionList;
	}

	public PlayerListPanel getPlayerListPanel() {
		return playerListPanel;
	}
	
	public PlayerDescription getSelectedPlayer() {
		int idx=this.playerListPanel.getJTable().getSelectedRow();
		if (idx!=-1) {
			return playerdescriptionList.getPlayerDescription(idx);
		} else {
			return null;
		}
	}

	/*public DetailedPlayerListTableModel getDetailedPlayerListTableModel() {
		return detailedPlayerListTableModel;
	}*/

	public void setPlayerdescriptionList(PlayerDescriptionList playerdescriptionList) {
		this.playerdescriptionList = playerdescriptionList;
		detailedPlayerListTableModel.setPlayerDescriptionList(this.playerdescriptionList);
	}

}
 