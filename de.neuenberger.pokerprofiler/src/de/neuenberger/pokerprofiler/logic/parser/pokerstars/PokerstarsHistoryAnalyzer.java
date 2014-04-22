package de.neuenberger.pokerprofiler.logic.parser.pokerstars;

import java.io.File;

import de.neuenberger.poker.common.model.Card;
import de.neuenberger.poker.common.model.Deck;
import de.neuenberger.poker.common.model.util.CardUtils;
import de.neuenberger.pokerprofiler.logic.analyzer.GameDescriptionLogic;
import de.neuenberger.pokerprofiler.logic.analyzer.GamePlayLogic;
import de.neuenberger.pokerprofiler.logic.parser.AnalysisState;
import de.neuenberger.pokerprofiler.logic.parser.BaseAsciiHistoryAnalyzer;
import de.neuenberger.pokerprofiler.model.Bet;
import de.neuenberger.pokerprofiler.model.GamePlay;

public class PokerstarsHistoryAnalyzer extends BaseAsciiHistoryAnalyzer {

	
	@Override
	public boolean acceptsFile(File file) {
		return !isTournementHHFile(file);
	}
	
	protected boolean isTournementHHFile(File file) {
		String name=file.getName();
		return name.matches("^HH\\d{8} T\\d{8} .*$");
	}

	private static PokerstarsHistoryAnalyzer instance=new PokerstarsHistoryAnalyzer();
	
	int dealer=-1;
	
	protected PokerstarsHistoryAnalyzer() {
		
	}
	
	@Override
	protected void analyzeBetString(String str) {
		GamePlay gp=getPlayerInString(str);
		if (gp!=null) {
			String relevantString=str.substring(gp.getPlayer().getName().length()+2).trim();
			
			Bet bet=getBetFromRelevantString(relevantString);
			this.putBet(gp, bet);
			dealer=-1;
			if (relevantString.startsWith("shows [") || relevantString.startsWith("mucked [")) {
				int idxFrom=relevantString.indexOf("[");
				int idxTo=relevantString.indexOf("]");
				
				String cardString = relevantString.substring(idxFrom+1,idxTo);
				
				Card c[]=Deck.getInstance().getCardFromSSString(cardString);
				gp.getPlayerCards()[0]=c[0];
				gp.getPlayerCards()[1]=c[1];
			} else if (relevantString.startsWith("collected $")) {
				String strArr[]=relevantString.split(" ");
				float pot=gp.getPot();
				pot+=Float.parseFloat(strArr[1].substring(1));
				gp.setPot(pot);
			}
		}
	}

	@Override
	protected void analyzeInitialization(String str) {
		if (str.matches("^Seat \\d{1,2}:.*$")) {
			int idxFrom=str.indexOf(": ")+2;
			int idxTo=str.indexOf(" ($");
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
		if (str.startsWith("Seat ")) {
			String strArr[]=str.split(" ");
			String number=strArr[1].substring(0, strArr[1].length()-1);
			int s=Integer.parseInt(number)-1;
			GamePlay gamePlay = gameDescription.getGamePlayArray()[s];
			if (gamePlay!=null) {
				String name=gamePlay.getPlayer().getName();
				int idx=str.indexOf(name);
				String relevantString=str.substring(idx+name.length()+1);
				Card ca[]=getCardsAfterShow(relevantString, "showed ");
				Card cb[]=getCardsAfterShow(relevantString, "mucked ");
				if (ca!=null) {
					gamePlay.getPlayerCards()[0]=ca[0];
					gamePlay.getPlayerCards()[1]=ca[1];
				} else if (cb!=null) {
					gamePlay.getPlayerCards()[0]=cb[0];
					gamePlay.getPlayerCards()[1]=cb[1];
				}
				
				if (relevantString.contains("collected (") || relevantString.contains("won (")) {
					String splitArr[]=relevantString.split(" ");
					boolean foundWord=false;
					for (int i=0; i<splitArr.length; i++) {
						String mPart=splitArr[i];
						if (mPart.equals("won")) {
							foundWord=true;
						} else if (mPart.equals("collected")) {
							foundWord=true;
						} else if (foundWord==true) {
							int sIdx=1;
							if (mPart.contains("$")) {
								sIdx=2;
							}
							gamePlay.setPot(Float.parseFloat(mPart.substring(sIdx,mPart.length()-1)));
							break;
						}
					}
				}
			}
		}
	}
	
	protected Card[] getCardsAfterShow(String relevantString, String syntax) {
		if (relevantString.contains(syntax)) {
			int idxShowed=relevantString.indexOf(syntax);
			String showString=relevantString.substring(idxShowed+syntax.length()+1);
			
			String cardString=showString.substring(0,5);
			return Deck.getInstance().getCardFromSSString(cardString);
		}
		return null;
	}

	@Override
	protected void checkAnalysisStatus(String str) {
		if (str.startsWith("PokerStars Game #")) {
			calculateUnAnswearedBets();
			newGameStarted(str.split(" ")[2].substring(1));
		} else if (analysisState==AnalysisState.STATE_SKIP) {
			
		} else if (str.startsWith("*** HOLE CARDS ***")) {
			analysisState=AnalysisState.STATE_PREFLOP;
			
		} else if (str.startsWith("*** FLOP *** [")) {
			analysisState=AnalysisState.STATE_FLOP;
			Card cards[]=Deck.getInstance().getCardFromSSString(str.substring(14,str.length()-1));
			gameDescription.getBoardCards()[0]=cards[0];
			gameDescription.getBoardCards()[1]=cards[1];
			gameDescription.getBoardCards()[2]=cards[2];
		} else if (str.startsWith("*** TURN *** [")) {
			analysisState=AnalysisState.STATE_TURN;
			Card cards[]=Deck.getInstance().getCardFromSSString(str.substring(25,str.length()-1));
			gameDescription.getBoardCards()[3]=cards[0];
		} else if (str.startsWith("*** RIVER *** [")) {
			analysisState=AnalysisState.STATE_RIVER;
			Card cards[]=Deck.getInstance().getCardFromSSString(str.substring(29,str.length()-1));
			gameDescription.getBoardCards()[4]=cards[0];
		} else if (str.startsWith("*** SUMMARY ***")) {
			analysisState=AnalysisState.STATE_SUMMARY;
		}
		
	}
	
	protected void calculateUnAnswearedBets() {
		if (gameDescription==null) {
			return;
		}
		if (gameDescription.isAnalyzed()) {
			return;
		}
		int playersAtSD=GameDescriptionLogic.countPlayersAtShowDown(gameDescription);
		
		if (playersAtSD>1) {
			return;
		}
		
		GamePlay[] gamePlayArray = gameDescription.getGamePlayArray();
		
		for (int i=0; i<gamePlayArray.length; i++) {
			GamePlay gamePlay = gamePlayArray[i];
			if (gamePlay!=null) {
				if (GamePlayLogic.isReachingShowDown(gamePlay)) {
					playersAtSD++;
				}
			}
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
		for (int i=0; i<strArr.length; i++) {
			if (strArr[i].startsWith("$")) {
				try {
					float v=Float.parseFloat(strArr[i].substring(1));
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
		return "Pokerstars";
	}

	public static PokerstarsHistoryAnalyzer getInstance() {
		return instance;
	}

	public String getConfigVar() {
		return "pokerstars.handhistory.directory";
	}

}