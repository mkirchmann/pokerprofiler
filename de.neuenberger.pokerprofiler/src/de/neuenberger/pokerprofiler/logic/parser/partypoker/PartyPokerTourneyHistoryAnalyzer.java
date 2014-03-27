package de.neuenberger.pokerprofiler.logic.parser.partypoker;

import java.io.File;

import de.neuenberger.pokerprofiler.model.Bet;

public class PartyPokerTourneyHistoryAnalyzer extends PartyPokerHistoryAnalyzer {

	@Override
	public boolean acceptsFile(File file) {
		return !isCashgameFile(file);
	}

	private static PartyPokerTourneyHistoryAnalyzer instance=new PartyPokerTourneyHistoryAnalyzer();
	@Override
	public String getGameType() {
		return "Tournement";
	}

	protected PartyPokerTourneyHistoryAnalyzer() {
		
	}

	public static PartyPokerTourneyHistoryAnalyzer getInstance() {
		return instance;
	}
	
	@Override
	protected Bet getBetFromRelevantString(String str) {
		String strArr[]=str.split(" ");
		
		Bet bet=null;
		
		if (str.equals("folds")) {
			bet=new Bet(Bet.TYPE_FOLD);
		} else if (str.startsWith("calls")) {
			bet=new Bet(Bet.TYPE_CALL);
		} else if (str.startsWith("bets")) {
			bet=new Bet(Bet.TYPE_BET);
		} else if (str.startsWith("checks")) {
			bet=new Bet(Bet.TYPE_CHECK);
		} else if (str.startsWith("raises")) {
			bet=new Bet(Bet.TYPE_RAISE);
		} else if (str.startsWith("is all-In")) {
			bet=new Bet(Bet.TYPE_ALL_IN);
		} else {
			bet=new Bet(Bet.TYPE_N_A);
		}
		for (int i=strArr.length-1; i>=0; i--) {
			if (strArr[i].startsWith("[") && strArr[i].endsWith("]")) {
				try {
					int length=strArr[i].length();
					float v=Float.parseFloat(strArr[i].substring(1,length-1).replace(",", ""));
					bet.setAmount(v);
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return bet;
	}
}
