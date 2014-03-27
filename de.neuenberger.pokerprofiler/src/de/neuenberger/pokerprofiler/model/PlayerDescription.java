package de.neuenberger.pokerprofiler.model;

import java.util.Vector;

public class PlayerDescription {
	Vector<GamePlay> gamePlayVector=new Vector<GamePlay>();
	
	String name;
	
	public PlayerDescription(String id) {
		name=id;
		
		
		System.out.println("Created player: "+id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean addGamePlay(GamePlay arg0) {
		return gamePlayVector.add(arg0);
	}

	public GamePlay getGamePlay(int arg0) {
		return gamePlayVector.get(arg0);
	}

	public int getSizeOfGamePlay() {
		return gamePlayVector.size();
	}
}
