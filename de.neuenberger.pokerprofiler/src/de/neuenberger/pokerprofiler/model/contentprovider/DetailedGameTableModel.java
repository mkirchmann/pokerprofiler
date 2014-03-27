package de.neuenberger.pokerprofiler.model.contentprovider;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import de.neuenberger.pokercalc.model.Card;
import de.neuenberger.pokerprofiler.logic.analyzer.BetLogic;
import de.neuenberger.pokerprofiler.logic.analyzer.GamePlayLogic;
import de.neuenberger.pokerprofiler.model.GameDescription;
import de.neuenberger.pokerprofiler.model.GamePlay;

public class DetailedGameTableModel extends DefaultTableModel {

	GameDescription gameDescription;

	public static final String COLUMN_NAME[]=new String[]{"Name","Pocket","Position","Res","Preflop","Flop","Turn","River"};
	
	
	public DetailedGameTableModel() {
		
	}

	public Class<?> getColumnClass(int c) {
		switch (c) {
		case 1:
			return Card[].class;
		case 3:
			return Float.class; 
		}
		return String.class;
	}

	public int getColumnCount() {
		return COLUMN_NAME.length;
	}

	public String getColumnName(int arg0) {
		return COLUMN_NAME[arg0];
	}

	public int getRowCount() {
		if (gameDescription==null) {
			return 0;
		} else {
			return gameDescription.getGamePlayArray().length;
		}
	}

	public Object getValueAt(int arg0, int arg1) {
		GamePlay gamePlay=gameDescription.getGamePlayArray()[arg0];
		if (gamePlay==null) {
			return null;
		}
		switch (arg1) {
			case 0:
				return gamePlay.getPlayer().getName();
			case 1:
				return gamePlay.getPlayerCards();
			case 2:
				return gamePlay.getPositionAsString();
			case 3:
				return new Float(gamePlay.getPot()+gamePlay.getUnansweared()-GamePlayLogic.getInvestedMoney(gamePlay));
			case 4:
				return BetLogic.getBetsAsString(gamePlay.getPreFlopBets());
			case 5:
				return BetLogic.getBetsAsString(gamePlay.getFlopBets());
			case 6:
				return BetLogic.getBetsAsString(gamePlay.getTurnBets());
			case 7:
				return BetLogic.getBetsAsString(gamePlay.getRiverBets());
		}
		
		return null;
	}

	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	

	public void setValueAt(Object arg0, int arg1, int arg2) {
		//
	}


	public GameDescription getGameDescription() {
		return gameDescription;
	}


	public void setGameDescription(GameDescription gameDescription) {
		this.gameDescription = gameDescription;
		this.fireTableChanged(new TableModelEvent(this));
	}
	
}
