package de.neuenberger.pokerprofiler.model;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import de.neuenberger.pokerprofiler.logic.analyzer.PokerTableDescriptionLogic;

public class PokerTableDescriptionChangeList extends DefaultTableModel {

	Vector<PokerTableDescription> vec=new Vector<PokerTableDescription>();
	HashMap<PokerTableDescription, Long> times=new HashMap<PokerTableDescription, Long>();
	String NAME_ARR[]=new String[]{"Name","PPF","FlopCost"};
	
	
	public PokerTableDescriptionChangeList() {
		ChangeListenerFarm.getInstance().addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent arg0) {
				if (arg0.getSource() instanceof PokerTableDescription) {
					PokerTableDescription ptd=(PokerTableDescription)arg0.getSource();
					addIfNotExists(ptd);
				}
			}
			
		});
	}
	
	protected void addIfNotExists(PokerTableDescription par_ptd) {
		boolean exists=false;
		for (int i=0; i<vec.size(); i++) {
			PokerTableDescription ptd = vec.get(i);
			if (ptd==par_ptd) {
				exists=true;
			}
		}
		if (exists==false) {
			vec.add(par_ptd);
			fireTableChanged(new TableModelEvent(this));
		}
		
		long current=System.currentTimeMillis();
		
		times.put(par_ptd,new Long(current));
		
		for (int i=vec.size()-1; i>=0; i--) {
			PokerTableDescription ptd = vec.get(i);
			Long x=times.get(ptd);
			if (x<current-360000) {
				vec.remove(ptd);
				fireTableChanged(new TableModelEvent(this));
			}
		}
	}
	
	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	public int getColumnCount() {
		return NAME_ARR.length;
	}

	public String getColumnName(int arg0) {
		return NAME_ARR[arg0];
	}

	public int getRowCount() {
		if (vec==null) {
			return 0;
		}
		return vec.size();
	}

	public Object getValueAt(int arg0, int arg1) {
		PokerTableDescription pokerTableDescription = vec.get(arg0);
		switch (arg1) {
		case 0:
			return pokerTableDescription.getName();
		case 1:
			return PokerTableDescriptionLogic.getPlayerSeeFlop(pokerTableDescription);
		case 2:
			return PokerTableDescriptionLogic.getFlopCost(pokerTableDescription);
		}
		return null;
	}

	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	public void setValueAt(Object arg0, int arg1, int arg2) {
		
	}
	
	public PokerTableDescription[] getTablesAsArray() {
		PokerTableDescription ptdArr[]=new PokerTableDescription[vec.size()];
		vec.toArray(ptdArr);
		return ptdArr;
	}
	
	public void removeAll() {
		vec.clear();
		fireTableChanged(new TableModelEvent(this));
	}

}
