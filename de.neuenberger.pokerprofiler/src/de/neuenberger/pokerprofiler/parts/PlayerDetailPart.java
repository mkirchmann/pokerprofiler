package de.neuenberger.pokerprofiler.parts;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.neuenberger.pokercalc.parts.IController;
import de.neuenberger.pokerprofiler.logic.analyzer.PlayerDescriptionLogic;
import de.neuenberger.pokerprofiler.model.GamePlay;
import de.neuenberger.pokerprofiler.model.PlayerDescription;
import de.neuenberger.pokerprofiler.model.contentprovider.DetailedPlayerTableModel;
import de.neuenberger.pokerprofiler.model.contentprovider.IContentFilter;
import de.neuenberger.pokerprofiler.model.contentprovider.filter.PlayerDescriptionFilter;
import de.neuenberger.pokerprofiler.model.contentprovider.filter.PokerTableFilter;
import de.neuenberger.pokerprofiler.ui.PlayerDetailPanel;

public class PlayerDetailPart implements IController {

	PlayerDetailPanel playerDetailPanel=new PlayerDetailPanel();
	DetailedPlayerTableModel detailedPlayerTableModel=new DetailedPlayerTableModel();
	PlayerDescriptionSummaryPart playerDescriptionSummaryPart=new PlayerDescriptionSummaryPart(false);
	
	
	PlayerDescription playerDescription;
	GamePlay gamePlay;
	IContentFilter iContentFilter;
	
	public PlayerDetailPart() {
		playerDetailPanel.getJTable().setModel(detailedPlayerTableModel);
		playerDetailPanel.add(playerDescriptionSummaryPart.getComponent(),BorderLayout.NORTH);
		
		
		playerDetailPanel.getJTable().getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent arg0) {
				int row=playerDetailPanel.getJTable().getSelectedRow();
				if (row!=-1) {
					if (iContentFilter==null) {
						setGamePlay(playerDescription.getGamePlay(row));
					} else {
						setGamePlay(detailedPlayerTableModel.getFilteredGames().get(row));
					}
					
				}
			}
			
		});
		
		playerDetailPanel.getJbClear().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				setIContentFilter(null);
			}
			
		});
		
		playerDetailPanel.getJbFilter().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				filterForSelectedPlayer();
			}
		});
	}
	
	protected void filterForSelectedPlayer() {
		this.setIContentFilter(new PlayerDescriptionFilter(getPlayerDescription()));
	}
	
	public Component getComponent() {
		return playerDetailPanel;
	}

	public void saveComponents() {
		// TODO Auto-generated method stub

	}

	/*public PlayerDetailPanel getPlayerDetailPanel() {
		return playerDetailPanel;
	}*/
	
	public void selectPlayer(PlayerDescription playerDescription) {
		detailedPlayerTableModel.setPlayerDescription(playerDescription);
		playerDescriptionSummaryPart.setPlayerDescription(playerDescription);
		this.playerDescription=playerDescription;
	}



	public PlayerDetailPanel getPlayerDetailPanel() {
		return playerDetailPanel;
	}



	public PlayerDescription getPlayerDescription() {
		return playerDescription;
	}



	public void setPlayerDescription(PlayerDescription playerDescription) {
		this.selectPlayer(playerDescription);
		
	}



	public GamePlay getGamePlay() {
		return gamePlay;
	}



	public void setGamePlay(GamePlay gamePlay) {
		this.gamePlay = gamePlay;
	}

	public IContentFilter getIContentFilter() {
		return iContentFilter;
	}



	public void setIContentFilter(IContentFilter contentFilter) {
		iContentFilter = contentFilter;
		
		this.detailedPlayerTableModel.setIContentFilter(iContentFilter);
		
		this.playerDetailPanel.getJbClear().setEnabled(iContentFilter!=null);
		this.playerDetailPanel.getJlStatus().setOpaque(iContentFilter!=null);
		PlayerDescriptionLogic.setContentFilter(contentFilter);
		if (contentFilter!=null) {
			this.playerDetailPanel.getJlStatus().setText(contentFilter.getFilterType());
			this.playerDetailPanel.getJlStatus().setToolTipText(contentFilter.getCriteriaDescription());			
		} else {
			this.playerDetailPanel.getJlStatus().setText("");
			this.playerDetailPanel.getJlStatus().setToolTipText("");
		}
		this.setPlayerDescription(playerDescription);
	}

} 