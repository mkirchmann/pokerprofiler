package de.neuenberger.pokerprofiler.model;

import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ChangeListenerFarm {
	Vector<ChangeListener> changeListenerVector=new Vector<ChangeListener>();
	
	private static final ChangeListenerFarm instance=new ChangeListenerFarm();
	
	private ChangeListenerFarm() {
		
	}
	
	
	public void fireChange(Object source) {
		ChangeEvent ce=new ChangeEvent(source);
		
		for (int i=0; i<changeListenerVector.size(); i++) {
			changeListenerVector.get(i).stateChanged(ce);
		}
	}
	
	public void addChangeListener(ChangeListener cl) {
		this.changeListenerVector.add(cl);
	}
	
	public void removeChangeListener(ChangeListener cl) {
		this.changeListenerVector.remove(cl);
	}


	public static ChangeListenerFarm getInstance() {
		return instance;
	}
}
