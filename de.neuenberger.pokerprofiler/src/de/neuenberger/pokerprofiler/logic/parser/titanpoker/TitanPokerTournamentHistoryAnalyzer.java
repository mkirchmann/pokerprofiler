package de.neuenberger.pokerprofiler.logic.parser.titanpoker;

import de.neuenberger.pokerprofiler.logic.analyzer.XMLTypeAHistoryAnalyzer;

public class TitanPokerTournamentHistoryAnalyzer extends XMLTypeAHistoryAnalyzer {

	private static TitanPokerTournamentHistoryAnalyzer instance=new TitanPokerTournamentHistoryAnalyzer();
	
	
	@Override
	public String getServer() {
		return "Titan";
	}

	public String getConfigVar() {
		return "titanpokertourn.handhistory.directory";
	}

	public String getGameType() {
		return "Tournament";
	}

	public static TitanPokerTournamentHistoryAnalyzer getInstance() {
		return instance;
	}

}
