package de.neuenberger.pokerprofiler.model.contentprovider;

import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import de.neuenberger.pokerprofiler.logic.analyzer.PlayerDescriptionLogic;
import de.neuenberger.pokerprofiler.model.ChangeListenerFarm;
import de.neuenberger.pokerprofiler.model.PlayerDescription;
import de.neuenberger.pokerprofiler.model.PlayerDescriptionList;

public class DetailedPlayerListTableModel implements TableModel {

	PlayerDescriptionList playerDescriptionList;
	
	Vector<TableModelListener> tableModelListenerList=new Vector<TableModelListener>(); 
	
	String columnName[]=new String[]{"Name","Class","Hands","Balance"};
	IContentFilter contentFilter=null;
	
	Vector<PlayerDescription> filteredPlayerDescriptionList=new Vector<PlayerDescription>();
	
	public DetailedPlayerListTableModel(PlayerDescriptionList pdl) {
		this.playerDescriptionList=pdl;
		
		
		ChangeListenerFarm clf=ChangeListenerFarm.getInstance();
		clf.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent arg0) {
				fireChange();
				
			}
			
		});
		
	}
	
	protected void fireChange() {
		TableModelEvent tme=new TableModelEvent(DetailedPlayerListTableModel.this);
		for (int i=0; i<tableModelListenerList.size(); i++) {
			tableModelListenerList.get(i).tableChanged(tme);
		}
	}
	
	
	
	
	public void addTableModelListener(TableModelListener arg0) {
		tableModelListenerList.add(arg0);

	}

	public Class<?> getColumnClass(int c) {
		if (c==3) {
			return Float.class;
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
		
		if (playerDescriptionList==null) {
			return 0;
		}
		if (contentFilter==null) {
			return playerDescriptionList.getSize();
		} else {
			filteredPlayerDescriptionList.clear();
			filteredPlayerDescriptionList.ensureCapacity(playerDescriptionList.getSize());
			for (int i=0; i<playerDescriptionList.getSize(); i++) {
				PlayerDescription obj=playerDescriptionList.getPlayerDescription(i);
				if (contentFilter.accepts(obj)) {
					filteredPlayerDescriptionList.add(obj);
				}
			}
			return filteredPlayerDescriptionList.size();
		}
	}

	public Object getValueAt(int arg1, int arg0) {
		PlayerDescription pd=null;
		if (contentFilter==null) {
			pd=playerDescriptionList.getPlayerDescription(arg1);
		} else {
			pd=filteredPlayerDescriptionList.get(arg1);
		}
		switch (arg0) {
		case 0:
			return pd.getName();
		case 1:
			return "N/A";
		case 2:
			return pd.getSizeOfGamePlay();
		case 3:
			return new Float(PlayerDescriptionLogic.sumCollectedPots(pd)-PlayerDescriptionLogic.sumInvestment(pd));
		}
		return null;
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

	public PlayerDescriptionList getPlayerDescriptionList() {
		return playerDescriptionList;
	}

	public void setPlayerDescriptionList(PlayerDescriptionList playerDescriptionList) {
		this.playerDescriptionList = playerDescriptionList;
		
		fireChange();
	}
	
	public void setFilter(IContentFilter filter) {
		this.contentFilter=filter;
		ChangeListenerFarm.getInstance().fireChange(this);
	}

}
