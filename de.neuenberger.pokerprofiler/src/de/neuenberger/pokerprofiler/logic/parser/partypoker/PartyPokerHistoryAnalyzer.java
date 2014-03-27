package de.neuenberger.pokerprofiler.logic.parser.partypoker;

import java.io.File;

import de.neuenberger.pokercalc.model.Card;
import de.neuenberger.pokercalc.model.Deck;
import de.neuenberger.pokerprofiler.logic.parser.AnalysisState;
import de.neuenberger.pokerprofiler.logic.parser.BaseAsciiHistoryAnalyzer;
import de.neuenberger.pokerprofiler.model.Bet;
import de.neuenberger.pokerprofiler.model.GamePlay;

public class PartyPokerHistoryAnalyzer extends BaseAsciiHistoryAnalyzer {

	
	

	private static PartyPokerHistoryAnalyzer instance=new PartyPokerHistoryAnalyzer();
	
	int dealer=-1;
	
	protected PartyPokerHistoryAnalyzer() {
		
	}
	
	@Override
	protected void analyzeBetString(String str) {
		GamePlay gp=getPlayerInString(str);
		if (gp!=null) {
			String relevantString=str.substring(gp.getPlayer().getName().length()+1);
			
			Bet bet=getBetFromRelevantString(relevantString);
			this.putBet(gp, bet);
			dealer=-1;
			if (relevantString.startsWith("shows [") || relevantString.startsWith("doesn't show")) {
				int idxFrom=relevantString.indexOf("[");
				int idxTo=relevantString.indexOf("]");
				
				String cardString = relevantString.substring(idxFrom+1,idxTo);
				
				Card c[]=Deck.getInstance().getCardFromCSString(cardString);
				gp.getPlayerCards()[0]=c[0];
				gp.getPlayerCards()[1]=c[1];
			} else if (relevantString.startsWith("wins $")) {
				String strArr[]=relevantString.split(" ");
				float pot=gp.getPot();
				pot+=Float.parseFloat(strArr[1].substring(1));
				gp.setPot(pot);
			} else if (relevantString.startsWith("wins ")) {
				String strArr[]=relevantString.split(" ");
				float pot=gp.getPot();
				pot+=Float.parseFloat(strArr[1].replace(",", ""));
				gp.setPot(pot);
			}
		}
	}

	@Override
	protected void analyzeInitialization(String str) {
		if (str.matches("^Seat \\d{1,2}:.*$")) {
			int idxFrom=str.indexOf(": ")+2;
			int idxTo=str.indexOf(" ( $");
			if (idxTo==-1) {
				idxTo=str.indexOf(" ( ");
			}
			int seatNo=Integer.parseInt(str.substring(5,idxFrom-2))-1;
			String nick=str.substring(idxFrom,idxTo);
			GamePlay gp=this.registerPlayer(nick, seatNo);
			
			if (dealer==seatNo) {
				gp.setPosition(GamePlay.POSITION_DEALER);
			}
		} else if (str.startsWith("Seat ") && str.endsWith(" is the button")) {
			String strArr[]=str.split(" ");
			
			dealer=Integer.parseInt(strArr[1])-1;
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

	private void printGameDescription() {
		for (int i=0; i<gameDescription.getGamePlayArray().length; i++) {
			GamePlay gp=gameDescription.getGamePlayArray()[i];
			if (gp!=null) {
				System.out.println("Seat "+(i+1)+": "+gp.getPlayer().getName()+" "+gp.getPosition());
			}
		}
		
	}

	protected GamePlay getPlayerInString(String str) {
		GamePlay gpArr[]=gameDescription.getGamePlayArray();
		for (int i=0; i<gpArr.length; i++) {
			if (gpArr[i]!=null) {
				String name=gpArr[i].getPlayer().getName();
				if (str.startsWith(name)) {
					return gpArr[i];
				}
			}
		}
		return null;
	}
	
	
	@Override
	protected void analyzeSummary(String str) {
		//
	}

	@Override
	protected void checkAnalysisStatus(String str) {
		if (str.startsWith("#Game No : ")) {
			newGameStarted(str.substring(11));
		} else if (analysisState==AnalysisState.STATE_SKIP) {
			
		} else if (str.startsWith("** Dealing down cards **")) {
			analysisState=AnalysisState.STATE_PREFLOP;
			
		} else if (str.startsWith("** Dealing Flop ** [")) {
			analysisState=AnalysisState.STATE_FLOP;
			Card cards[]=Deck.getInstance().getCardFromCSString(str.substring(20,str.length()-2));
			gameDescription.getBoardCards()[0]=cards[0];
			gameDescription.getBoardCards()[1]=cards[1];
			gameDescription.getBoardCards()[2]=cards[2];
		} else if (str.startsWith("** Dealing Turn ** [")) {
			analysisState=AnalysisState.STATE_TURN;
			Card cards[]=Deck.getInstance().getCardFromCSString(str.substring(20,str.length()-2));
			gameDescription.getBoardCards()[3]=cards[0];
		} else if (str.startsWith("** Dealing River **")) {
			analysisState=AnalysisState.STATE_RIVER;
			Card cards[]=Deck.getInstance().getCardFromCSString(str.substring(21,str.length()-2));
			gameDescription.getBoardCards()[4]=cards[0];
		}
		
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
			if (strArr[i].startsWith("[$")) {
				try {
					float v=Float.parseFloat(strArr[i].substring(2).replace(",", ""));
					bet.setAmount(v);
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return bet;
	}

	public String getGameType() {
		return "Cashgame";
	}

	public String getServer() {
		return "PartyPoker";
	}

	public static PartyPokerHistoryAnalyzer getInstance() {
		return instance;
	}

	public String getConfigVar() {
		return "partypoker.handhistory.directory";
	}

	@Override
	public boolean acceptsFile(File file) {
		return isCashgameFile(file);
	}
	
	protected boolean isCashgameFile(File file) {
		return (file.getName().startsWith("Table "));
	}
	
}
