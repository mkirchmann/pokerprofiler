package de.neuenberger.pokerprofiler.model;

import java.util.HashMap;

public class PlayerDescriptionListPool {
	//      HIST_ID        PLAYER_ID
	private static HashMap<String, PlayerDescriptionList> hashMap=new HashMap<String, PlayerDescriptionList>();
	
	public static PlayerDescription getPlayerDescription(String serverId, String playerId) {
		PlayerDescriptionList hm =getPlayerDescriptionList(serverId);
		
		
		
		PlayerDescription pd=hm.getPlayerDescription(playerId);
		
		return pd;
	}
	
	public static PlayerDescriptionList getPlayerDescriptionList(String serverId) {
		PlayerDescriptionList hm = hashMap.get(serverId);
		if (hm==null) {
			hm=new PlayerDescriptionList();
			
			hashMap.put(serverId,hm);
		}
		return hm;
	}
}
