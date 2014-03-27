package de.neuenberger.pokerprofiler.parts.util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.neuenberger.pokerprofiler.model.ChangeListenerFarm;
import de.neuenberger.pokerprofiler.model.GameDescription;
import de.neuenberger.pokerprofiler.model.PokerTableDescription;
import de.neuenberger.pokerprofiler.parts.hud.TableHUDPart;
import de.neuenberger.pokerprofiler.ui.hud.TableOverlaySetupFrame;

public class TableHUDManager {
	private static TableHUDManager instance=new TableHUDManager();

	HashMap<PokerTableDescription, TableHUDPart> hashMapTtC=new HashMap<PokerTableDescription, TableHUDPart>();
	
	private TableHUDManager() {
		ChangeListenerFarm.getInstance().addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent arg0) {
				if (arg0.getSource() instanceof GameDescription) {
					updateAll();
				} else if (arg0.getSource() instanceof PokerTableDescription) {
					updateAll();
				} else {
					System.out.println("Not updated: "+arg0.getSource());
				}
				
			}
			
		});
		Timer timer=new Timer(15000, new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Collection<TableHUDPart> name = hashMapTtC.values();
				TableHUDPart ptArr[]=new TableHUDPart[name.size()];
				name.toArray(ptArr);
				for (int i=0; i<ptArr.length; i++) {
					ptArr[i].refreshComponents();
				}
			}
			
		});
		timer.start();
	}
	
	public void closeAll() {
		Collection<TableHUDPart> name = hashMapTtC.values();
		TableHUDPart ptArr[]=new TableHUDPart[name.size()];
		name.toArray(ptArr);
		for (int i=0; i<ptArr.length; i++) {
			if (ptArr[i].isComponentsVisible()) {
				ptArr[i].setComponentsVisible(false);
			}
		}
		hashMapTtC.clear();
	}
	
	protected void updateAll() {
		Collection<TableHUDPart> name = hashMapTtC.values();
		TableHUDPart ptArr[]=new TableHUDPart[name.size()];
		name.toArray(ptArr);
		for (int i=0; i<ptArr.length; i++) {
			GameDescription description = ptArr[i].getGameDescription();
			if (description!=null) {
				if (description.getPokerTableDescription().getLastGameDescription()!=description) {
					GameDescription lastGameDescription = description.getPokerTableDescription().getLastGameDescription();
					if (lastGameDescription!=null) {
						ptArr[i].setGameDescription(lastGameDescription);
					}
				}
			}
		}
	}
	
	public void open(PokerTableDescription description) {
		GameDescription gameDescription = description.getLastGameDescription();
		TableHUDPart tableHUDP = hashMapTtC.get(description);
		
		if (tableHUDP==null) {
			tableHUDP=new TableHUDPart();
			
			JFrame jFrame=tableHUDP.getTableOverlaySetupFrame();
			jFrame.setLocation(0,0);
			jFrame.setSize(200,100);
		
			hashMapTtC.put(description, tableHUDP);
		}
		tableHUDP.setup();
		tableHUDP.setGameDescription(description.getLastGameDescription());
	}
	
	public static TableHUDManager getInstance() {
		return instance;
	}

	public void update(PokerTableDescription description) {
		GameDescription gameDescription = description.getLastGameDescription();
		TableHUDPart tableHUDP = hashMapTtC.get(description);
		if (tableHUDP!=null) {
			tableHUDP.setGameDescription(gameDescription);
		}
		
	}
	
	public boolean isOpened(PokerTableDescription description) {
		GameDescription gameDescription = description.getLastGameDescription();
		TableHUDPart tableHUDP = hashMapTtC.get(description);
		if (tableHUDP==null) {
			return false;
		} else {
			return tableHUDP.isComponentsVisible();
		}
	}
}
