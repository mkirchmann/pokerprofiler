package de.neuenberger.pokerprofiler.parts;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.neuenberger.pokercalc.parts.IController;
import de.neuenberger.pokerprofiler.logic.parser.IHistoryAnalyzer;
import de.neuenberger.pokerprofiler.logic.parser.ParserPool;
import de.neuenberger.pokerprofiler.model.GameDescription;
import de.neuenberger.pokerprofiler.model.GamePlay;
import de.neuenberger.pokerprofiler.model.PlayerDescription;
import de.neuenberger.pokerprofiler.model.PlayerDescriptionList;
import de.neuenberger.pokerprofiler.model.PokerTableDescription;
import de.neuenberger.pokerprofiler.model.contentprovider.DetailedPlayerListTableModel;
import de.neuenberger.pokerprofiler.model.contentprovider.filter.PokerTableFilter;
import de.neuenberger.pokerprofiler.ui.ProfileMainPanel;

public class ProfilerMainPart implements IController {

	
	ProfileMainPanel profileMainPanel=new ProfileMainPanel(); 
	
	PlayerDetailPart playerDetailPart=new PlayerDetailPart();
	PlayerListPart playerListPart=new PlayerListPart();
	GamePlayTablePart gamePlayTablePart=new GamePlayTablePart();
	
	DetailedPlayerListTableModel detailedPlayerListTableModel=new DetailedPlayerListTableModel(new PlayerDescriptionList());
	
	CurrentPokerTableListPart currentPokerTableListPart=new CurrentPokerTableListPart();
	
	public ProfilerMainPart() {
		
		
		
		JInternalFrame jif;
		
		int xdiv=700;
		int leftw=700;
		int rightw=500;
		
		jif=profileMainPanel.addComponentAsInternalFrame(playerDetailPart.getComponent());
		jif.setTitle("Player Details");
		jif.setLocation(0,300);
		jif.setSize(leftw,400);
		jif=profileMainPanel.addComponentAsInternalFrame(playerListPart.getComponent());
		jif.setTitle("List of Players");
		jif.setLocation(xdiv,0);
		jif.setSize(rightw,600);
		jif=profileMainPanel.addComponentAsInternalFrame(gamePlayTablePart.getComponent());
		jif.setTitle("Game Play");
		jif.setLocation(0,0);
		jif.setSize(leftw,300);
		jif=profileMainPanel.addComponentAsInternalFrame(currentPokerTableListPart.getComponent());
		jif.setTitle("Current Tables");
		jif.setLocation(xdiv,600);
		jif.setSize(rightw,100);
		
		setup();
	}
	
	
	
	
	private void setup() {
		playerListPart.getPlayerListPanel().getJTable().getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent arg0) {
				PlayerDescription pd=playerListPart.getSelectedPlayer();
				if (pd!=null) {
					playerDetailPart.selectPlayer(pd);
				}
			}
			
		});
		
		playerDetailPart.getPlayerDetailPanel().getJTable().getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent arg0) {
				GamePlay gp=playerDetailPart.getGamePlay();
				if (gp!=null) {
					gamePlayTablePart.setGameDescription(gp.getGameDescription());
				}
			}
			
		});
		
		
		Timer timer=new Timer(5000,new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				if (profileMainPanel.getJcbAutoUpdate().isSelected()) {
					ParserPool.getInstance().update();
				}
				
			}
			
		});
		timer.start();
		
		gamePlayTablePart.getGamePlayTablePanel().getJTable().getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent arg0) {
				int idx=gamePlayTablePart.getGamePlayTablePanel().getJTable().getSelectedRow();
				if (idx!=-1) {
					GamePlay gpArr[]=gamePlayTablePart.getGameDescription().getGamePlayArray();
					if (gpArr[idx]!=null) {
						playerDetailPart.setPlayerDescription(gpArr[idx].getPlayer());
					}
				}
				
			}
			
		});
		
		currentPokerTableListPart.getJTable().getSelectionModel().addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent arg0) {
				int idx=currentPokerTableListPart.getJTable().getSelectedRow();
				if (idx!=-1) {
					PokerTableDescription[] tablesAsArray = currentPokerTableListPart.getPokerTableDescriptionChangeList().getTablesAsArray();
					GameDescription lastGameDescription = tablesAsArray[idx].getLastGameDescription();
					
					gamePlayTablePart.setGameDescription(lastGameDescription);
				}
			}
			
		});
		
		profileMainPanel.getJcbServer().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				modeChanged();
			}
			
		});
		
		
		// putting modes to the server combo
		IHistoryAnalyzer[] analyzers = ParserPool.getInstance().getRegisteredIds();
		for (int i=0; i<analyzers.length; i++) {
			profileMainPanel.getJcbServer().addItem(analyzers[i]);
		}
		profileMainPanel.getJcbServer().setSelectedIndex(0);
		
		gamePlayTablePart.getGamePlayTablePanel().getJbFilter().addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				PokerTableDescription description = gamePlayTablePart.getGameDescription().getPokerTableDescription();
				
				playerDetailPart.setIContentFilter(new PokerTableFilter(description));
			}
			
		});
	}




	protected void modeChanged() {
		final IHistoryAnalyzer iha=(IHistoryAnalyzer)profileMainPanel.getJcbServer().getSelectedItem();
		if (iha!=null) {
			Runnable runnable=new Runnable() {

				public void run() {
					StatusPart.getInstance().setBusy(true, "Updating mode");
					ParserPool pool = ParserPool.getInstance();
					pool.setSelectedHistoryAnalyzer(iha);
					pool.update();
					playerListPart.setPlayerdescriptionList(iha.getPlayerDescriptionList());
					StatusPart.getInstance().setBusy(false, null);
					
				}
				
			};
			Thread thread=new Thread(runnable);
			thread.start();
			
		}
	}




	public Component getComponent() {
		return profileMainPanel;
	}

	public void saveComponents() {
		playerDetailPart.saveComponents();
		playerListPart.saveComponents();
		gamePlayTablePart.saveComponents();

	}

}
