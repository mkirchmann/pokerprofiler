package de.neuenberger.pokerprofiler.logic.parser.cdpoker;

import de.neuenberger.pokerprofiler.logic.analyzer.XMLTypeAHistoryAnalyzer;

public class CDPokerTournamentHistoryAnalyzer extends XMLTypeAHistoryAnalyzer {

	private static CDPokerTournamentHistoryAnalyzer instance=new CDPokerTournamentHistoryAnalyzer();
	private CDPokerTournamentHistoryAnalyzer() {
		
	}
	
	public String getConfigVar() {
		return "cdpokertourn.handhistory.directory";
	}

	public String getGameType() {
		return "Tournament";
	}

	public static CDPokerTournamentHistoryAnalyzer getInstance() {
		return instance;
	}

}
