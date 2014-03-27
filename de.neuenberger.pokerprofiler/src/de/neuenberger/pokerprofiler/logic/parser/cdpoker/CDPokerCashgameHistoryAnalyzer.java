package de.neuenberger.pokerprofiler.logic.parser.cdpoker;

import de.neuenberger.pokerprofiler.logic.analyzer.XMLTypeAHistoryAnalyzer;


public class CDPokerCashgameHistoryAnalyzer extends XMLTypeAHistoryAnalyzer {

	private static CDPokerCashgameHistoryAnalyzer instance=new CDPokerCashgameHistoryAnalyzer();
	
	private CDPokerCashgameHistoryAnalyzer() {
		
	}
	
	
	public String getConfigVar() {
		return "cdpokercash.handhistory.directory";
	}

	public String getGameType() {
		return "Cashgame";
	}


	public static CDPokerCashgameHistoryAnalyzer getInstance() {
		return instance;
	}

}
