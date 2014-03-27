package de.neuenberger.pokerprofiler.logic.parser.pokerstars;

import java.io.File;

import de.neuenberger.pokerprofiler.model.Bet;
import de.neuenberger.pokerprofiler.model.GamePlay;

public class PokerstarsTourneyHistoryAnalyzer extends PokerstarsHistoryAnalyzer {
	
	
	
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
		for (int i=0; i<strArr.length; i++) {
			if (strArr[i].matches("^\\d*$")) {
				try {
					String substring = strArr[i];
					float v=Float.parseFloat(substring);
					bet.setAmount(v);
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return bet;
	}


	@Override
	public String getGameType() {
		return "Tournement";
	}


	@Override
	protected void analyzeInitialization(String str) {
		if (str.matches("^Seat \\d{1,2}:.*$")) {
			int idxFrom=str.indexOf(": ")+2;
			int idxTo=str.indexOf(" (");
			if (idxTo==-1 || idxFrom==1) {
				System.out.println("unknown string: "+str);
			} else {
				int seatNo=Integer.parseInt(str.substring(5,idxFrom-2))-1;
				String nick=str.substring(idxFrom,idxTo);
				GamePlay gp=this.registerPlayer(nick, seatNo);
				
				if (dealer==seatNo) {
					gp.setPosition(GamePlay.POSITION_DEALER);
				}
			}
		} else if (str.startsWith("Table '") && str.endsWith(" is the button")) {
			String strArr[]=str.split(" ");
			
			dealer=Integer.parseInt(strArr[strArr.length-4].substring(1))-1;
		} else {
			GamePlay gp=getPlayerInString(str);
			if (gp!=null) {
				String relevantString=str.substring(gp.getPlayer().getName().length());
				Bet bet=getBetFromRelevantString(relevantString);
				if (relevantString.contains("posts small blind")) {
					gp.setPosition(GamePlay.POSITION_SMALLBLIND);
					gp.setBlind(bet.getAmount());
				} else if (relevantString.contains("posts big blind")) {
					gp.setPosition(GamePlay.POSITION_BIGBLIND);
					gp.setBlind(bet.getAmount());
				}
			}
		}
		
	}


	@Override
	public boolean acceptsFile(File file) {
		return this.isTournementHHFile(file);
	}


	private static PokerstarsTourneyHistoryAnalyzer instance=new PokerstarsTourneyHistoryAnalyzer();
	
	
	protected PokerstarsTourneyHistoryAnalyzer() {
		
	}
	
	public static PokerstarsHistoryAnalyzer getInstance() {
		return instance;
	}
}
