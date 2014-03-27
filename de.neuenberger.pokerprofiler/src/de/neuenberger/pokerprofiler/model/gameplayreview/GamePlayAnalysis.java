package de.neuenberger.pokerprofiler.model.gameplayreview;

import de.neuenberger.pokerprofiler.model.GamePlay;

public class GamePlayAnalysis {
	GamePlay gamePlay;
	
	public GamePlayAnalysis(GamePlay gp) {
		this.gamePlay=gp;
	}

	public GamePlay getGamePlay() {
		return gamePlay;
	}
}
