package de.neuenberger.pokerprofiler.model.gameplayreview;

import de.neuenberger.pokerprofiler.model.Bet;

public class BetAnalysis {
	Bet bet;
	
	float ev;
	float currentPot;
	
	public BetAnalysis(Bet b) {
		this.bet=b;
	}
}
