package de.neuenberger.pokerprofiler.model;

import de.neuenberger.pokercalc.model.Card;

public class GameDescription {
	
	protected PokerTableDescription pokerTableDescription;
	protected String gameKey;
	protected boolean isAnalyzed=false;

	protected GamePlay gamePlayArray[]=new GamePlay[10];
	protected Card boardCards[]=new Card[5];
	
	
	public GameDescription(PokerTableDescription ptd, String gameKey) {
		this.pokerTableDescription=ptd;
		this.gameKey=gameKey;
		ChangeListenerFarm.getInstance().fireChange(ptd);
	}
	
	
	public boolean isAnalyzed() {
		return isAnalyzed;
	}

	public void setAnalyzed(boolean isAnalyzed) {
		this.isAnalyzed = isAnalyzed;
	}

	public PokerTableDescription getPokerTableDescription() {
		return pokerTableDescription;
	}


	public Card[] getBoardCards() {
		return boardCards;
	}
	
	public void registerGamePlay(GamePlay gp, int seat) {
		gamePlayArray[seat]=gp;
	}


	public GamePlay[] getGamePlayArray() {
		return gamePlayArray;
	}


	public String getGameKey() {
		return gameKey;
	}
}
