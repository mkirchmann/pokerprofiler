package de.neuenberger.pokerprofiler.logic.parser;

import java.io.File;
import java.util.HashMap;

import de.neuenberger.pokerprofiler.model.ChangeListenerFarm;
import de.neuenberger.pokerprofiler.model.GameDescription;
import de.neuenberger.pokerprofiler.model.GamePlay;
import de.neuenberger.pokerprofiler.model.PlayerDescription;
import de.neuenberger.pokerprofiler.model.PlayerDescriptionList;
import de.neuenberger.pokerprofiler.model.PlayerDescriptionListPool;
import de.neuenberger.pokerprofiler.model.PokerTableDescription;

abstract public class AbstractHistoryAnalyzer implements IHistoryAnalyzer {
	
	private HashMap<String,PokerTableDescription> pokerTableDescriptionHashMap=new HashMap<String,PokerTableDescription>();
	protected PokerTableDescription pokerTableDescription;
	protected GameDescription gameDescription;


	public String getParserId() {
		return getServer()+" "+getGameType();
	}
	
	protected GamePlay registerPlayer(String nick, int seat) {
		PlayerDescription playerDescription=PlayerDescriptionListPool.getPlayerDescription(getParserId(), nick);
		GamePlay gamePlay=new GamePlay(playerDescription,gameDescription);
		gameDescription.registerGamePlay(gamePlay, seat);
		playerDescription.addGamePlay(gamePlay);
		return gamePlay;
	}
	
	protected GamePlay getPlayerFromNick(String nick) {
		return this.getPlayerFromNick(nick, false);
	}
	protected GamePlay getPlayerFromNick(String nick, boolean debug) {
		GamePlay[] gamePlayArray = gameDescription.getGamePlayArray();
		for (int i=0; i<gamePlayArray.length; i++) {
			if (gamePlayArray[i]!=null) {
				String registered=gamePlayArray[i].getPlayer().getName();
				if (debug==true) {
					System.out.println("search for: "+nick+" ?=? "+registered);
				}
				if (registered.equals(nick)) {
					return gamePlayArray[i];
				}
			}
		}
		if (debug==false) {
			// this.getPlayerFromNick(nick, true);
		}
		return null;
	}
	
	public PlayerDescriptionList getPlayerDescriptionList() {
		return PlayerDescriptionListPool.getPlayerDescriptionList(this.getParserId());
	}
	
	public String toString() {
		return this.getParserId();
	}
	
	protected PokerTableDescription getPokerTableDescriptionFromString(String str) {
		PokerTableDescription ptd=pokerTableDescriptionHashMap.get(str);
		if (ptd==null) {
			ptd=new PokerTableDescription(str);
			pokerTableDescriptionHashMap.put(str,ptd);
		}
		return ptd;
	}
	
	protected void newGameStarted(String gameKey) {
		if (gameDescription!=null) {
			ChangeListenerFarm.getInstance().fireChange(gameDescription);
			gameDescription.setAnalyzed(true);
		}
		gameDescription=pokerTableDescription.getGameDescription(gameKey);
		
	}
	
	protected String getKeyFromFile(File file) {
		return file.getName();
	}

}
