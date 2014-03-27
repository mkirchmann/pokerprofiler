package de.neuenberger.pokerprofiler.model.contentprovider;

import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import de.neuenberger.pokercalc.model.Card;
import de.neuenberger.pokercalc.model.util.CardUtils;
import de.neuenberger.pokerprofiler.logic.analyzer.GamePlayLogic;
import de.neuenberger.pokerprofiler.model.ChangeListenerFarm;
import de.neuenberger.pokerprofiler.model.GamePlay;
import de.neuenberger.pokerprofiler.model.PlayerDescription;

public class DetailedPlayerTableModel implements TableModel{

	Vector<TableModelListener> tableModelListenerList=new Vector<TableModelListener>();
	
	PlayerDescription playerDescription;
	
	public static final String columnName[]=new String[]{"Cards","Position","Board","Pot","Invested","Preflop","Flop","Turn","River"};
	
	
	Vector<GamePlay> filteredGames=new Vector<GamePlay>();
	IContentFilter iContentFilter=null;
	
	public DetailedPlayerTableModel(PlayerDescription pd) {
		playerDescription=pd;
	}
	
	
	public DetailedPlayerTableModel() {
		ChangeListenerFarm clf=ChangeListenerFarm.getInstance();
		clf.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent arg0) {
				fireChange();
				
			}
			
		});
	}
	
	
	protected void fireChange() {
		TableModelEvent tme=new TableModelEvent(DetailedPlayerTableModel.this);
		for (int i=0; i<tableModelListenerList.size(); i++) {
			tableModelListenerList.get(i).tableChanged(tme);
		}
	}


	public void addTableModelListener(TableModelListener arg0) {
		tableModelListenerList.add(arg0);
		
	}

	public Class<?> getColumnClass(int c) {
		switch (c) {
		case 0:
		case 2:
			return Card[].class;
		}
		return String.class;
	}

	public int getColumnCount() {
		return columnName.length;
	}

	public String getColumnName(int arg0) {
		return columnName[arg0];
	}

	public int getRowCount() {
		if (playerDescription==null) {
			return 0;
		} else if (iContentFilter==null) {
			return playerDescription.getSizeOfGamePlay();
		} else {
			filteredGames.clear();
			filteredGames.ensureCapacity(playerDescription.getSizeOfGamePlay());
			
			for (int i=0; i<playerDescription.getSizeOfGamePlay(); i++) {
				GamePlay gamePlay = playerDescription.getGamePlay(i);
				if (iContentFilter.accepts(gamePlay)) {
					filteredGames.add(gamePlay);
				}
			}
			
			return filteredGames.size();
			
		}
	}

	public Object getValueAt(int y,int x) {
		
		GamePlay gp=null;
		
		if (iContentFilter==null) {
			gp=this.playerDescription.getGamePlay(y);
		} else {
			gp=filteredGames.get(y);
		}
		
		switch (x) {
		case 0:
			return gp.getPlayerCards();
		case 1:
			return gp.getPositionAsString();
		case 2:
			return gp.getGameDescription().getBoardCards();
		case 3:
			return ""+(gp.getPot()+gp.getUnansweared());
		case 4:
			return GamePlayLogic.getInvestedMoney(gp);
		case 5:
			return GamePlayLogic.getSummedPreFlopBets(gp);
		case 6:
			return GamePlayLogic.getSummedFlopBets(gp);
		case 7:
			return GamePlayLogic.getSummedTurnBets(gp);
		case 8:
			return GamePlayLogic.getSummedRiverBets(gp);
		}
		return "N/A";
	}

	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	public void removeTableModelListener(TableModelListener arg0) {
		tableModelListenerList.remove(arg0);
		
	}

	public void setValueAt(Object arg0, int arg1, int arg2) {
		//
	}


	public PlayerDescription getPlayerDescription() {
		return playerDescription;
	}


	public void setPlayerDescription(PlayerDescription playerDescription) {
		this.playerDescription = playerDescription;
		
		fireChange();
	}


	public IContentFilter getIContentFilter() {
		return iContentFilter;
	}


	public void setIContentFilter(IContentFilter contentFilter) {
		iContentFilter = contentFilter;
		fireChange();
	}


	public Vector<GamePlay> getFilteredGames() {
		return filteredGames;
	}

}
