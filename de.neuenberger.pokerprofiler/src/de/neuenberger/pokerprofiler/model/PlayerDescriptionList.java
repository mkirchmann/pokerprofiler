package de.neuenberger.pokerprofiler.model;

import java.util.HashMap;
import java.util.Vector;

import sun.misc.Compare;
import sun.misc.Sort;

public class PlayerDescriptionList {
	
	HashMap<String,PlayerDescription> hashMap=new HashMap<String,PlayerDescription>();
	
	Vector<PlayerDescription> vec=new Vector<PlayerDescription>();
	
	
	public void addPlayerDescription(PlayerDescription pd) {
		hashMap.put(pd.getName(),pd);
		vec.add(pd);
		
		ChangeListenerFarm.getInstance().fireChange(this);
	}
	
	public PlayerDescription getPlayerDescription(String playerId) {
		PlayerDescription pd=hashMap.get(playerId);
		if (pd==null) {
			pd=new PlayerDescription(playerId);
			this.addPlayerDescription(pd);
		}
		return pd;
	}
	
	public void sort() {
		PlayerDescription pdArr[]=new PlayerDescription[vec.size()];
		vec.toArray(pdArr);
		
		Sort.quicksort(pdArr, new Compare(){
			public int doCompare(Object arg0, Object arg1) {
				PlayerDescription pdA=(PlayerDescription)arg0;
				PlayerDescription pdB=(PlayerDescription)arg1;
				return pdA.getName().toLowerCase().compareTo(pdB.getName().toLowerCase());
			}
		});
		
		vec.removeAllElements();
		vec.ensureCapacity(pdArr.length);
		for (int i=0; i<pdArr.length; i++) {
			vec.add(pdArr[i]);
		}
	}

	public PlayerDescription getPlayerDescription(int arg0) {
		return vec.get(arg0);
	}

	public int getSize() {
		return vec.size();
	}
}
