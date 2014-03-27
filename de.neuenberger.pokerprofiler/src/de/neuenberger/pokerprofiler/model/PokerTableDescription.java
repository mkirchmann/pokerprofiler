package de.neuenberger.pokerprofiler.model;

import java.util.Collection;
import java.util.HashMap;

public class PokerTableDescription {
	HashMap<String, GameDescription> gameDescriptionHashMap=new HashMap<String, GameDescription>();
	
	String name;
	
	GameDescription lastGameDescription;
	
	public PokerTableDescription(String name) {
		this.name=name;
	}
	
	public GameDescription getGameDescription(String key) {
		lastGameDescription = gameDescriptionHashMap.get(key);
		if (lastGameDescription==null) {
			lastGameDescription=new GameDescription(this,key);
			gameDescriptionHashMap.put(key,lastGameDescription);
			//ChangeListenerFarm.getInstance().fireChange(this);
		}
		return lastGameDescription;
	}
	
	public GameDescription[] getGameDescriptionArray() {
		Collection<GameDescription> coll = gameDescriptionHashMap.values();
		GameDescription gdArr[]=new GameDescription[coll.size()];
		coll.toArray(gdArr);
		return gdArr;
	}

	public GameDescription getLastGameDescription() {
		return lastGameDescription;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
