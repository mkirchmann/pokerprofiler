package de.neuenberger.pokerprofiler.logic.parser.titanpoker;

import de.neuenberger.pokerprofiler.logic.analyzer.XMLTypeAHistoryAnalyzer;

public class TitanPokerCashgameHistoryAnalyzer extends XMLTypeAHistoryAnalyzer{

	private static TitanPokerCashgameHistoryAnalyzer instance=new TitanPokerCashgameHistoryAnalyzer();
	
	@Override
	public String getServer() {
		return "Titan";
	}

	public String getConfigVar() {
		return "titanpokercash.handhistory.directory";
	}

	public String getGameType() {
		return "Cashgame";
	}

	public static TitanPokerCashgameHistoryAnalyzer getInstance() {
		return instance;
	}

}
