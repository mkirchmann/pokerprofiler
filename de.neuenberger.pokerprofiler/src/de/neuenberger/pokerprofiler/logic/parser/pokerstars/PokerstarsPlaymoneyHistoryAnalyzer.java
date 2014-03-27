package de.neuenberger.pokerprofiler.logic.parser.pokerstars;

import java.io.File;

public class PokerstarsPlaymoneyHistoryAnalyzer extends PokerstarsTourneyHistoryAnalyzer {
	
	private static PokerstarsPlaymoneyHistoryAnalyzer instance = new PokerstarsPlaymoneyHistoryAnalyzer();
	
	@Override
	public String getGameType() {
		return "Playmoney";
	}
	
	@Override
	public boolean acceptsFile(File file) {
		return isPlayMoneyGame(file);
	}
	
	protected boolean isPlayMoneyGame(File file) {
		String name=file.getName();
		boolean matches = name.matches("^HH\\d{8} .*?Play Money.*$");
		return matches;
	}

	public static PokerstarsPlaymoneyHistoryAnalyzer getInstance() {
		return instance;
	}
}
